package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import model.MethodElement;

public class ReplaceMethodVisitor extends ASTVisitor {
   private MethodElement curProgElem;
   private List<String> parameters;

   private ASTRewrite rewrite;
   private AST astCUnit;

   public ReplaceMethodVisitor(MethodElement curProgElem, List<String> parameters) {
      this.curProgElem = curProgElem;
      this.parameters = parameters;
   }

   public boolean visit(MethodDeclaration node) {
      if (checkMethod(node)) {
         // new name
         /* Object value = astCUnit.newSimpleName(this.newMethodName);
         rewrite.set(node, MethodDeclaration.NAME_PROPERTY, value, null); */
         // parameter supporting primitive types
         ListRewrite parameterListRewrite = rewrite.getListRewrite(node, MethodDeclaration.PARAMETERS_PROPERTY);
         // remove all
         for (int i = 0; i < node.parameters().size(); i++) {
            ASTNode p = (ASTNode) node.parameters().get(i);
            parameterListRewrite.remove(p, null);
         }
         // append new parameters
         for (String parm : parameters) {
            String[] var = parm.trim().split("\\s");
            String type = var[0];
            SingleVariableDeclaration newSVD = astCUnit.newSingleVariableDeclaration();
            newSVD.setName(astCUnit.newSimpleName(var[1]));
            if (type.equals("int")) {
               newSVD.setType(astCUnit.newPrimitiveType(PrimitiveType.INT));
               parameterListRewrite.insertLast(newSVD, null);
            }
         }
      }
      return true;
   }

   private boolean checkMethod(MethodDeclaration md) {
      boolean check1 = this.curProgElem.getMethodName().equals(md.getName().getFullyQualifiedName());
      // boolean check2 = this.curProgElem.getParameterSize().equals(md.parameters().size());
      if (check1 /*&& check2*/) {
         return true;
      }
      return false;
   }

   public void setAST(AST ast) {
      this.astCUnit = ast;
   }

   public void ASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }
}
