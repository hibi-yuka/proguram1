package newlang4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PushbackReader;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

	        LexicalAnalyzer lex;
	        LexicalUnit		first;
	        Environment		env;

	        System.out.println("basic parser");//解析開始

	        String path = "C:\\Users\\c0117312\\Desktop\\t.txt";
			File file = new File(path);
			FileReader fr = null;
			try {
				fr = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			PushbackReader pr = new PushbackReader(fr);
	        lex = new LexicalAnalyzerImpl(pr); //読み込まれた値や文字がlexへ渡されていく,lexにはLexicalType.○○と字句が渡されてる

	        env = new Environment(lex); //読み込んだものを次にenvへ（ここで渡すのは読み込んだものの）

	        first = lex.get();//LexicalAnalayzerで読み込んで出力される形をfirstへ
	        lex.unget(first);//LexicalUnit型の変数が渡されている


	        if (Program.isFirst(first)) {//<program>というfirst集合に入っているかを確認する

	        	Node handler = Program.getHandler(first, env);//最初の字句とその型、読み込んだ文字の二つを渡す。envにはLexicalAnalyzerがあるのでそこからgetする。オブジェクトを作成

	        	handler.parse();//作成したオブジェクトをparseへ

	        	System.out.println(handler); //handler.toStringで出力される
	        }
	        else System.out.println("syntax error");//firts集合に該当するものなし＝解析できないのでアウト
	}

}

//分かっていない事
//結局engには何が入っている？
//インスタンスを引数に渡した時に何が渡される？どういう挙動をする？
//目指すべき出力する形
//parseの条件