package ink.pd2.psh.core;

import ink.pd2.shell.core.Mark;

import java.util.Random;

public class Main {
	protected static Random random = new Random(new Random(
					Main.class.hashCode() + new Random().nextInt()).nextLong());

	//TODO 重构项目
	public static void main(String[] args) {
		//TODO 主方法
		System.out.println(Mark.INS.update(
				"&color:red.null[Sorry, but there is no functions in the new core yet.]&"));
	}
}
