/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.MethodElement;
import model.ProgElementModelProvider;
import model.ProgramElement;

public class DeclarationVisitor extends ASTVisitor {
   private String pkgName;
   private String className;
   private String methodName;
   private ProgramElement pkgElem;
   private ProgramElement classElem;
   private MethodElement methodElem;

   public DeclarationVisitor() {
   }

   @Override
   public boolean visit(PackageDeclaration pkgDecl) {
      this.pkgName = pkgDecl.getName().getFullyQualifiedName();
      this.pkgElem = new ProgramElement(pkgName);
      ProgramElement prePkg = ProgElementModelProvider.INSTANCE.addProgramElement(this.pkgElem);
      if (prePkg != null) {
         this.pkgElem = prePkg;
      }
      return super.visit(pkgDecl);
   }

   /**
    * A type declaration is the union of a class declaration and an interface declaration.
    */
   @Override
   public boolean visit(TypeDeclaration typeDecl) {
      this.className = typeDecl.getName().getIdentifier();
      this.classElem = new ProgramElement(className, this.pkgElem);
      this.pkgElem.add(classElem);
      return super.visit(typeDecl);
   }

   @Override
   public boolean visit(MethodDeclaration methodDecl) {
      this.methodName = methodDecl.getName().getIdentifier();
      this.methodElem = new MethodElement(methodName, this.classElem);
      this.methodElem.setParameters(methodDecl.parameters());
      String className = methodDecl.resolveBinding().getDeclaringClass().getName();
      this.methodElem.setClassName(className);
      String pkgName = methodDecl.resolveBinding().getDeclaringClass().getPackage().getName();
      this.methodElem.setPkgName(pkgName);
      this.classElem.add(methodElem);
      return super.visit(methodDecl);
   }
}
