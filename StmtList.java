package newlang4;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class StmtList extends Node {

	Environment env;
	public List<Node> handlerlist = new ArrayList<Node>();

	public StmtList(Environment env) {//コンストラクタ
		this.env = env;
		type = NodeType.STMT_LIST;
	}

	static final Set<LexicalType> fristSet =  EnumSet.of(
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END,
			LexicalType.IF,
			LexicalType.WHILE,
			LexicalType.DO,
			LexicalType.NL
			);

	public static boolean isFirst(LexicalUnit lu) {//isFistメソッドでlu
		return fristSet.contains(lu.getType()); //リストが特定の要素を含むか判定
	}

	public static StmtList getHandler(LexicalUnit first, Environment env) { //ここでは引数が二つ渡されている。最初に読み込んだ
		return new StmtList(env);//StmtListクラスをインスタンス化する
	}

	public boolean parse() throws Exception{//ここでツリーを作る
		Node handler;

		while(true) { //stmtである限り繰り返す StmtList以下のNodeのparseはこのループに入って下にいくを繰り返す
			LexicalUnit first = env.getInput().get();

			if(first.getType() == LexicalType.NL) {//NLを読み飛ばす作業
				continue; //処理を抜けて再び上に戻る
			}else {
				env.getInput().unget(first);
			}

			System.out.println(first + " :Stmt_list");//出力テスト

			if(Stmt.isFirst(first)) {//first集合がStmtと一致するならtrueで中の操作
				handler = Stmt.getHandler(first , env);//インスタンスを生成する
				System.out.println(first + " :StmtList.Stmt");//出力テスト
				handler.parse();//インスタンス.メソッドでStmt.parseを実行
				handlerlist.add(handler);//何故や？
			}else if(Block.isFirst(first)) {//Blockと一致するならこっちの処理
				handler = Block.getHandler(first, env);//インスタンス生成
				System.out.println(first + " :StmtList.Block1");//出力テスト
				handler.parse(); //handlerはローカル変数でいい
				handlerlist.add(handler);//listとする
			}else {
			break;
			}
		}
		return true;//Programのparseメソッドに結果を返す
	}

//バグを抱えている。のちのち被害を受けるのでその時にどうにかしよう。今は完成させるのを目的とする
	public String toString() {
		String str = "[";
		boolean f = true;
		for(Node child : handlerlist) { //拡張for文 handlerlistに含まれる内容をchildへ代入
			if (f) f = false; 
			else str += ",";
			str += child.toString();//childの内容を出力
		}
		str += "]";
		return str;
		//return    str + ")" + handler.toString();
		//return handler.toString();//handler.toStringではない　今の俺のコードでは
	}
}

//while文、上から実行された時に、if文の結果に関係なく、最後のreturn flaseが実行されている
//いくらもリストにならない。breakをいれる
	//処理に困ったらbreak
//54と60でreturnするとだめ