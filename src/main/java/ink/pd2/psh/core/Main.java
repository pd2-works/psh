package ink.pd2.psh.core;

import ink.pd2.shell.core.Mark;

import java.util.Random;

public final class Main {
	protected static final int PSH = ModuleBoard.newRandomId();
	protected static Random random = new Random(new Random(
					Main.class.hashCode() + new Random().nextInt()).nextLong());

	//TODO 重构项目
	public static void main(String[] args) {
		//根权限授权
		Permission.PermissionInfo rootPermission = new Permission.PermissionInfo();
		rootPermission.set(Permission.ALLOW_ALL, true);
		Permission.putInfo(PSH, rootPermission);

		//TODO 主方法
		System.out.println(Mark.INS.update(
				"&color:red.null[Sorry, but there is no functions in the new core yet.]&"));
	}
}
