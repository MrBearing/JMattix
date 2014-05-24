package net.mrbearing.mattix.util;

import java.io.*;
import java.net.*;

/**
 * xmlファイルからElementの読み込み、Elementからxmlファイルへ書き出しを行うことができます。
 * 標準のxmlファイルではありません。キー、要素、は全て[key]要素[/key]の形式（入れ子構造）
 * で、[key /]や[key value1=a value2=2]などの形式は使えません。
 */
public class XMLIO
{
	/**
	 * インスタンス作成禁止
	 */
	private XMLIO(){}

	/**
	 * xmlファイルからElementを読み込みます
	 * @param file 入力ファイル
	 * @return 取得したElement
	 */
	public static Element read( File file ) throws Exception
	{
		return read( file.toURI().toURL() );
	}

	/**
	 * xmlファイルのファイル名からElementを読み込みます
	 * @param filename 入力ファイル名
	 * @return 取得したElement
	 */
	public static Element read( String filename ) throws Exception
	{
		return read( new File( filename ).toURI().toURL() );
	}

	/**
	 * xmlファイルのURLからElementを取得します
	 * @param url xmlファイルのURL
	 * @return 取得したElement
	 */
	public static Element read( URL url ) throws Exception
	{
		String encoding = getEncoding( url );
		
		BufferedReader br;
		if( encoding == null )
		{
			br = new BufferedReader( new InputStreamReader( url.openStream(), "Shift-JIS" ) );
		}else
			br = new BufferedReader( new InputStreamReader( url.openStream(), encoding ) );
		
		StringBuffer data = new StringBuffer();
		String tmp;
		while( (tmp = br.readLine()) != null )
			data.append( tmp );

		int start = data.indexOf( "<?" );
		int end = data.indexOf( "?>" );
		if( start != -1 && end != -1 )
			data.delete( start, end + 2 );

		int index;
		while( (index=data.indexOf("\t")) != -1 )
			data.deleteCharAt( index );

//System.out.println( "src: "+data );

		return new Element( data );
	}

	/**
	 * ファイルの先頭部分の<?... encoding="UTF-8" ...?>の部分を読むことで
	 * urlのエンコーディングを取得します（xmlファイルのエンコーディング判別用です）
	 * 表記がない場合はnullを返します
	 * @param url 入力ファイルのURL
	 * @return エンコーディング
	 */
	public static String getEncoding( URL url )
	{
		String result = null;
		try{
			BufferedReader br = new BufferedReader( new InputStreamReader( url.openStream() ) );
			boolean flag = true;
			String tmp;
			while( flag && ((tmp=br.readLine()) != null) )
			{
				int start = tmp.indexOf( "<?" );
				int end = tmp.indexOf( "?>" );
				if( start !=-1 && end != -1 )
				{
					start = tmp.indexOf( "encoding" ) + 8;
					tmp = tmp.substring( start, end );
					while( tmp.startsWith( " " ) ) tmp = tmp.substring( 1, tmp.length() );
					if( tmp.startsWith( "=" ) )
					{
						tmp = tmp.substring( 1, tmp.length() );
						while( tmp.startsWith( " " ) ) tmp = tmp.substring( 1, tmp.length() );
						end = Math.max( tmp.indexOf( " " ), tmp.length() );
						result = tmp.substring( 0, end );
						result = result.replaceAll( "\"", "" );
					}else
						return null;
				}
			}
			br.close();
		}catch( Exception e )
		{
			result = null;
		}
		return result;
	}

	/**
	 * Element.toString()によって出力される文字列（xml形式）をファイルに書き込みます
	 * @param element ファイルに書き出したいElement
	 * @param file 書き出したいファイル
	 */
	public static void write( Element element, File file ) throws Exception
	{
		BufferedWriter bw = new BufferedWriter( new FileWriter( file ) );
		bw.write( element.toString() );
		bw.close();
	}

	/**
	 * テストを実行します。
	 * 内容は、config.xmlを読み込み、プロンプトに出力します。
	 */
	public static void main(String[] args)
	{
		try{
			System.out.println( "テストを実行します。(config.xmlファイルを読み、出力します)" );
			Element element = XMLIO.read( new File( "config.xml" ).toURI().toURL() );
			
			System.out.println( element );
			
			String el = element.getChild( "webcam" ).getChild("source").getChildValue("type");
			System.out.println( el );
			
			XMLIO.write( element, new File( "new_config.xml" ) );
		}catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}