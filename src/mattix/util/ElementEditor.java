package mattix.util;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * Elementを表示、値の編集をGUIで行うクラスです。また、ファイルへの読み込み、書き込みも簡単に行うことができます。
 */
public class ElementEditor extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static final float TEXTSIZE = 20;
    Element element_;
    JTree tree_;
    public JFrame frame_;
    JPanel controlpanel_;
    File lastdirectory_;

    // アンチエイリアスの設定
    static {
        System.setProperty("swing.aatext", "true");
    }

    /**
     * 新しくElementEditorを作成します
     */
    public ElementEditor() {
        controlpanel_ = new JPanel(new FlowLayout());
        init();
        setPreferredSize(new Dimension(300, 500));
    }

    /**
     * Elementを指定してElementEditorを作成します
     *
     * @param element
     *            表示するElement
     */
    public ElementEditor(Element element) {
        this();
        setElement(element);
    }

    /**
     * Elementを配置しないレイアウトの初期化をします。
     */
    public void init() {
        removeAll();
        setLayout(new BorderLayout());
        add(controlpanel_, BorderLayout.NORTH);
    }

    /**
     * Elementを設定して、再レイアウトします
     *
     * @param element
     *            表示するElement
     */
    public void setElement(Element element) {
        element_ = element;
        element_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree_.treeDidChange();
            }
        });

        TreeModel model = new TreeModel() {
            public Object getRoot() {
                return element_;
            }

            public int getChildCount(Object parent) {
                Element[] es = ((Element) parent).getChildren();
                return es.length;
            }

            public Object getChild(Object parent, int index) {
                Element[] es = ((Element) parent).getChildren();
                return es[index];
            }

            public int getIndexOfChild(Object parent, Object child) {
                Element[] es = ((Element) parent).getChildren();
                for (int i = 0; i < es.length; i++)
                    if (es[i] == child)
                        return i;
                return -1;
            }

            public boolean isLeaf(Object node) {
                return (((Element) node).getValue() != null);
            }

            public void valueForPathChanged(TreePath path, Object newValue) {
            }

            public void addTreeModelListener(TreeModelListener l) {
            }

            public void removeTreeModelListener(TreeModelListener l) {
            }
        };
        tree_ = new JTree(model);
        tree_.setCellRenderer(new TreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean selected, boolean expanded,
                    boolean leaf, int row, boolean hasFocus) {
                JLabel label = new JLabel();
                label.setFont(getFont().deriveFont(TEXTSIZE));
                label.setText(((Element) value).getKey());
                if (leaf)
                    label.setText(label.getText() + " : "
                            + ((Element) value).getValue());
                return label;
            }
        });
        tree_.setCellEditor(new TreeCellEditor() {
            ArrayList<CellEditorListener> listeners;
            Element target;

            public Component getTreeCellEditorComponent(JTree tree,
                    Object value, boolean isSelected, boolean expanded,
                    boolean leaf, int row) {
                target = (Element) value;
                JLabel label = new JLabel();
                label.setText(((Element) value).getKey());
                label.setFont(getFont().deriveFont(TEXTSIZE));
                if (!leaf)
                    return label;
                label.setText(label.getText() + " : ");
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(new Color(200, 200, 255));
                final JTextField tf = new JTextField(20);
                tf.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        target.setValue(tf.getText());
                        for (int i = 0; i < listeners.size(); i++)
                            (listeners.get(i))
                                    .editingStopped(new ChangeEvent(this));
                    }
                });
                tf.setText(((Element) value).getValue());
                tf.setFont(getFont().deriveFont(TEXTSIZE - 5));
                panel.add(label, BorderLayout.WEST);
                panel.add(tf, BorderLayout.CENTER);
                return panel;
            }

            public void addCellEditorListener(CellEditorListener l) {
                if (listeners == null)
                    listeners = new ArrayList<CellEditorListener>();
                listeners.add(l);
            }

            public void cancelCellEditing() {
            }

            public Object getCellEditorValue() {
                return null;
            }

            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            public void removeCellEditorListener(CellEditorListener l) {
                listeners.remove(l);
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            public boolean stopCellEditing() {
                return true;
            }
        });
        tree_.setEditable(true);
        removeAll();
        setLayout(new BorderLayout());
        add(new JScrollPane(tree_), BorderLayout.CENTER);
        add(controlpanel_, BorderLayout.SOUTH);
        if (frame_ != null)
            frame_.pack();
    }

    /**
     * タイトルを指定してフレームを表示します
     *
     * @param exit_on_close
     *            フレームを閉じたときにプログラムを終了するかどうかを示します
     */
    public void showFrame(String title, boolean exit_on_close) {
        if (frame_ == null) {
            frame_ = new JFrame(title);
            if (exit_on_close)
                frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame_.setContentPane(this);
        }
        frame_.pack();
        frame_.setVisible(true);
    }

    /**
     * フレームを表示します
     *
     * @param exit_on_close
     *            フレームを閉じたときにプログラムを終了するかどうかを示します
     */
    public void showFrame(boolean exit_on_close) {
        showFrame("ElementEditor", exit_on_close);
    }

    /**
     * コントロールパネルの部分にコンポーネントを追加します
     */
    public void addControlPanel(JComponent component) {
        controlpanel_.add(component);
    }

    /**
     * コントロールパネルをリセットします
     */
    public void resetControlPanel() {
        controlpanel_.removeAll();
    }

    /**
     * 表示しているElementを返します
     *
     * @return Element
     */
    public Element getElement() {
        return element_;
    }

    /**
     * 現在のElementの保存アクションを返します
     *
     * @return 保存アクション
     */
    public Action getSaveAction() {
        Action action = new AbstractAction("Save") {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                String title;
                String msg;
                int msgtype;
                if (getElement() == null) {
                    title = "エラー";
                    msg = "データがありません";
                    msgtype = JOptionPane.ERROR_MESSAGE;
                } else {
                    JFileChooser filechooser = new JFileChooser(lastdirectory_);
                    if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        lastdirectory_ = filechooser.getCurrentDirectory();
                        try {
                            File file = filechooser.getSelectedFile();
                            int value = 0;
                            if (file.exists())
                                value = JOptionPane.showConfirmDialog(null,
                                        "ファイル( " + file + " )が存在します。上書きしますか？",
                                        "警告", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                            if (value == 0) {
                                save(file);
                                title = "成功";
                                String data = getElement().toString();
                                if (data.length() > 500)
                                    data = data.substring(0, 500) + "\n...";
                                msg = "ファイル( " + file + " )へ書き込みました\n[内容]\n"
                                        + data;
                                msgtype = JOptionPane.INFORMATION_MESSAGE;
                            } else {
                                title = "中断";
                                msg = "書き込みを中断しました";
                                msgtype = JOptionPane.INFORMATION_MESSAGE;
                            }
                        } catch (Exception exc) {
                            title = "エラー";
                            msg = "ファイルへの書き込みに失敗しました\n\t" + exc.getMessage();
                            msgtype = JOptionPane.ERROR_MESSAGE;
                        }
                    } else {
                        title = "中断";
                        msg = "書き込みを中断しました";
                        msgtype = JOptionPane.INFORMATION_MESSAGE;
                    }
                }
                JOptionPane.showMessageDialog(null, msg, title, msgtype);
            }
        };
        return action;
    }

    /**
     * 現在のElementの読み込みアクションを返します
     *
     * @return 読み込みアクション
     */
    public Action getOpenAction() {
        Action action = new AbstractAction("Open") {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser(lastdirectory_);
                if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    lastdirectory_ = filechooser.getCurrentDirectory();
                    try {
                        java.io.File file = filechooser.getSelectedFile();
                        open(file);
                    } catch (Exception exc) {
                        String title = "エラー";
                        String msg = "ファイルへの読み込みに失敗しました\n\t" + exc.getMessage();
                        int msgtype = JOptionPane.ERROR_MESSAGE;
                        JOptionPane
                                .showMessageDialog(null, msg, title, msgtype);
                    }
                }
            }
        };
        return action;
    }

    /**
     * ファイルに書き出します
     */
    public void save(java.io.File file) throws Exception {
        XMLIO.write(getElement(), file);
    }

    /**
     * ファイルから読み込みます
     */
    public void open(java.io.File file) throws Exception {
        setElement(XMLIO.read(file));
    }

    /**
     * テストを実行します
     */
    public static void main(String[] args) throws Exception {
        // try{UIManager.setLookAndFeel(
        // UIManager.getSystemLookAndFeelClassName() );}catch( Exception e ){}
        // Element configdata = XMLIO.read( "config.xml" );
        ElementEditor ee = new ElementEditor();
        ee.addControlPanel(new JButton(ee.getOpenAction()));
        ee.addControlPanel(new JButton(ee.getSaveAction()));
        ee.showFrame(true);
    }
}