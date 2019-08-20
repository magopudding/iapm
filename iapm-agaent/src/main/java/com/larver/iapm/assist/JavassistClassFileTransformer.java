package com.larver.iapm.assist;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * @ClassName: JavaAssistClassFileTransformer
 * @Descraption: TODO
 * @Author: kongxiangyun
 * @Date: 2019/8/19 17:13
 * @Version 1.0
 **/
public class JavassistClassFileTransformer implements ClassFileTransformer {

	private ClassPool classPool = ClassPool.getDefault();

	private String PACKAGE_NAME = "com/yixin/vehicletypelib/service/style";
	private String PACKAGE_NAME1 = "com.yixin.vehicletypelib.service.style";
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain, byte[] classfileBuffer)
		throws IllegalClassFormatException {

		System.out.println("Tranforming start "+ className);

		if(!className.startsWith(PACKAGE_NAME)){
			return null;
		}
		className = className.replaceAll("/",".");
		System.out.println("Tranforming start newClassName"+ className);

		ClassPool poll = ClassPool.getDefault();
		CtClass ctClass = null;

		poll.insertClassPath(new LoaderClassPath(loader));

		try {
			ctClass = poll.get(className);
		} catch (NotFoundException e) {
			ByteArrayInputStream is = null;
			try {
				is = new ByteArrayInputStream(classfileBuffer);
				try {
					ctClass  = poll.makeClass(is);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}finally {
				if(null != is){
					try {
						is.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					is = null;
				}
			}
			e.printStackTrace();
		}
		if(ctClass.isInterface()){
			return null;
		}
		CtMethod[] methods = ctClass.getDeclaredMethods();
		for (CtMethod method : methods) {
			try {
				enhance(method);
			} catch (CannotCompileException e) {
				e.printStackTrace();
			}
		}
		try {
			return ctClass.toBytecode();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
			return null;
		}finally {
			if(null != ctClass){
				ctClass.detach();
			}
		}
		return null;
	}

	private void enhance(CtMethod method) throws CannotCompileException {
		method.insertBefore("{  System.out.println(\""+method.getLongName()+"called...\");}");
		method.instrument(new ExprEditor(){
			@Override
			public void edit(MethodCall m) throws CannotCompileException {
				if(m.getClassName().startsWith(PACKAGE_NAME1)){
					StringBuilder sb = new StringBuilder();
					sb.append("{long startTime = System.currentTimeMillis();");
					sb.append("$_=$proceed($$) + \" amended...\";");
					sb.append("System.out.println(\"");
					sb.append(m.getClassName()).append(".").append(m.getMethodName());
					sb.append(" cost: \" + (System.currentTimeMillis() - startTime) + \" ms\");}");
					m.replace(sb.toString());
				}
			}
		});

	}
}
