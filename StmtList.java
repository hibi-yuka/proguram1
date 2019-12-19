package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtList extends Node {

	Environment env;
	Node handler;

	public StmtList(Environment env) {
		this.env = env;
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

	public boolean parse() throws Exception{

	LexicalUnit first = env.getInput().get();
	env.getInput().unget(first);
	System.out.println(first);
		//ここでツリーを作る


	    if(Stmt.isFirst(first)) {
			handler = Stmt.getHandler(first , env);
			return handler.parse();

	}
	return false;
}

	public String toString() {
		return "Stmt_List" + "" +  handler.toString();
	}
}

