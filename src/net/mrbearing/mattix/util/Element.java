package net.mrbearing.mattix.util;

import java.io.*;
import java.awt.event.*;
import java.util.*;

/**
 * 設定データなどの保存を目的としたデータモデルです。 一つの Element は key と value もしくはkeyとchildren を持っています。
 * keyは項目名、valueは項目の値、項目が木構造となる場合は 複数の子供children( Element の配列)を持つことができます。
 * valueとchildrenを同時に持つことはありません。
 */
public class Element {
    private Element[] children_;
    private String key_;
    private String value_;
    ArrayList<ActionListener> actionlistenerlist_;
    private static boolean lsamekeyflag_;
    private static boolean samekeyflag_;
    private static String samekeybuf_;

    public Element() {
    }

    /**
     * 独自のxml形式の文字列（String型）を指定してElementを作成します
     * ファイル、URLからElementを作成する場合XMLIOクラスが使用できます
     *
     * @param data
     *            独自のxml形式の文字列
     */
    public Element(String data) throws Exception {
        this(new StringBuffer(data));
    }

    /**
     * 独自のxml形式の文字列（StringBuffer型）を指定してElementを作成します
     * ファイル、URLからElementを作成する場合XMLIOクラスが使用できます
     *
     * @param data
     *            独自のxml形式の文字列
     */
    public Element(StringBuffer data) throws Exception {
        value_ = "";
        ArrayList<Element> list = new ArrayList<Element>();

        String tmp = null;

        if ((key_ = getNextKey(data)) != null) {
            if (key_.startsWith("/")) {
                StringBuffer restdata = new StringBuffer(data.toString());
                int counter = 50;
                while (restdata.length() > counter) {
                    restdata.insert(counter, "\n");
                    counter += 50;
                }
                String msg = "<" + key_ + ">かその直前のタグに対応するタグが開かれていません。\n";
                if (lsamekeyflag_)
                    msg += "直前で2度同じキーのタグが開かれました(" + samekeybuf_ + ")\n";
                msg += "場所は以下の文の直前です。";
                throw new Exception(msg + "\n---------\n" + restdata
                        + "\n----------\n\n");
            }

            while (true) {
                tmp = getNextKey(data);

                if (tmp.equals("/" + key_))
                    break;

                lsamekeyflag_ = samekeyflag_;
                samekeyflag_ = (tmp != null && tmp.equals(key_));
                if (samekeyflag_)
                    samekeybuf_ = key_;

                Element child = new Element(data.insert(0, "<" + tmp + ">"));
                list.add(child);
            }
        }

        if (list.size() != 0) {
            children_ = new Element[list.size()];
            for (int i = 0; i < children_.length; i++)
                children_[i] = (Element) list.get(i);
        }

        if (children_ != null)
            value_ = null;
        else {
            if (value_ == null)
                value_ = "";
            else if (value_.indexOf("\n") != -1)
                value_ = value_.substring(0, value_.indexOf("\n"));
        }
    }

    /**
     * xml文字列の先頭から、次の項目（キー）を検索します
     *
     * @param data
     *            独自のxml形式の文字列
     * @return 次のキー
     */
    private String getNextKey(StringBuffer data) {
        int keystart = data.indexOf("<");
        if (keystart == -1)
            return null;
        value_ += data.substring(0, keystart);
        int keyend = data.indexOf(">", keystart);
        if (keyend == -1)
            return null;
        String key = data.substring(keystart + 1, keyend);
        data.delete(0, keyend + 1);
        return key;
    }

    /**
     * 値が変更されるのを検出するためのもの
     *
     * @param actionlistener
     *            アクションを検出するリスナー
     */
    public void addActionListener(ActionListener actionlistener) {
        if (actionlistenerlist_ == null)
            actionlistenerlist_ = new ArrayList<ActionListener>();
        actionlistenerlist_.add(actionlistener);
        Element[] children = getChildren();
        if (children != null)
            for (int i = 0; i < children.length; i++)
                children[i].addActionListener(actionlistener);
    }

    /**
     * 更新が必要であることをリスナーに通知します
     */
    private void update() {
        if (actionlistenerlist_ != null)
            for (int i = 0; i < actionlistenerlist_.size(); i++)
                ((ActionListener) actionlistenerlist_.get(i))
                        .actionPerformed(new ActionEvent(this,
                                ActionEvent.ACTION_PERFORMED, "update"));
    }

    /**
     * ElementからPropetiesを作成します。このとき、childrenは無視されます。
     * つまり、Elementの項目名と項目の値からなるPropetiesを作成します。
     *
     * @return Propeties
     */
    public java.util.Properties createPorpeties() {
        java.util.Properties properties = new java.util.Properties();
        for (int i = 0; i < children_.length; i++)
            properties.setProperty(children_[i].getKey(), children_[i]
                    .getValue());
        return properties;
    }

    /**
     * 項目名を設定します
     *
     * @param key
     *            項目名
     */
    public void setKey(String key) {
        key_ = key;
        update();
    }

    /**
     * 項目名を取得します
     *
     * @return 項目名
     */
    public String getKey() {
        return key_;
    }

    /**
     * このEmementのvalueを設定します
     *
     * @param value
     *            設定する値
     */
    public void setValue(String value) {
        value_ = value;
        update();
    }

    /**
     * このEmementのvalueを取得します
     *
     * @return valueの値
     */
    public String getValue() {
        return value_;
    }

    /**
     * このElementがもつ子供の配列（Element[]）を設定します
     *
     * @param children
     *            設定する子供の配列
     */
    public void setChildren(Element[] children) {
        children_ = children;
        update();
    }

    /**
     * このElementがもつ子供の配列（Element[]）を取得します
     *
     * @return 子供の配列
     */
    public Element[] getChildren() {
        return children_;
    }

    /**
     * このElementが持つ子供の中で、指定したkeyを持つ子供のみの配列（Element[]）を返します
     *
     * @param childrenkey
     *            子供が持つkey
     * @return keyがchildrenkeyである子供の配列
     */
    public Element[] getChildren(String childrenkey) {
        ArrayList<Element> list = new ArrayList<Element>();
        for (int i = 0; i < children_.length; i++)
            if (children_[i].getKey().equals(childrenkey))
                list.add(children_[i]);
        if (list.size() == 0)
            return null;
        Element[] selectedchildren = new Element[list.size()];
        for (int i = 0; i < list.size(); i++)
            selectedchildren[i] = (Element) list.get(i);
        return selectedchildren;
    }

    /**
     * このElementが持つ子供の中で、指定したkeyを持つ一番初めに記述された子供を返します
     *
     * @param childkey
     *            子供が持つkey
     * @return 一番初めに見つかったkeyがchildrenkeyである子供
     */
    public Element getChild(String childkey) {
        if (children_ == null)
            return null;
        for (int i = 0; i < children_.length; i++)
            if (children_[i].getKey().equals(childkey))
                return children_[i];
        return null;
    }

    /**
     * このElementが持つ子供の中で、指定したkeyを持つ子供のvalueによる配列（String[]）を返します
     *
     * @param childkey
     *            子供が持つkey
     * @return keyがchildrenkeyである子供の値の配列
     */
    public String[] getChildValues(String childkey) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < children_.length; i++)
            if (children_[i].getKey().equals(childkey))
                list.add(children_[i].getValue());
        String[] buf = new String[list.size()];
        for (int i = 0; i < buf.length; i++)
            buf[i] = (String) list.get(i);
        return buf;
    }

    /**
     * このElementが持つ子供の中で、指定したkeyを持つ一番初めに記述された子供のvalueを返します
     *
     * @param childkey
     *            子供が持つkey
     * @return 一番初めに見つかったkeyがchildrenkeyである子供の値
     */
    public String getChildValue(String childkey) {
        for (int i = 0; i < children_.length; i++)
            if (children_[i].getKey().equals(childkey))
                return children_[i].getValue();
        return null;
    }

    /**
     * スペースを出力します
     *
     * @param count
     *            スペースを入れる数
     * @return 指定された数のスペース
     */
    private String putSpace(int count) {
        String result = "";
        for (int i = 0; i < count; i++)
            result += "\t";
        return result;
    }

    /**
     * このElementをXML形式のStringで返します
     *
     * @return このElementのxml形式
     */
    public String toString() {
        return toString(0);
    }

    /**
     * 左側に指定されたスペースを入れて、ElementをXML形式で返します（\r\nバージョン）
     *
     * @param count
     *            スペースを入れる数
     * @return このElementのxml形式
     */
    private String toString(int space) {
        StringBuffer buf = new StringBuffer();
        buf.append("<" + key_ + ">");
        if (children_ != null) {
            buf.append("\r\n");
            space++;
            for (int i = 0; i < children_.length; i++)
                buf.append(putSpace(space) + children_[i].toString(space));
            space--;
            buf.append(putSpace(space));
        } else if (value_ != null)
            buf.append(value_);
        buf.append("</" + key_ + ">\r\n");

        return buf.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println("テストを実行します。(config.xmlファイルを読み、出力します)");
            //Element element = XMLIO.read(new File("Readme.html").toURI().toURL());
            Element element = XMLIO.read(new File("./ini/GamePropety.xml"));
            System.out.println(element);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}