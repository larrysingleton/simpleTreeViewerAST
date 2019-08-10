/**
 */
package util;

import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * @author
 * @date
 * @since J2SE-1.8
 */
public class UtilAST {
   static final int INVALID_DOC = -1;
   static String fileContents = null;

   public static ASTParser parse() {
      ASTParser parser = ASTParser.newParser(AST.JLS11);
      configParser(parser);
      return parser;
   }

   public static CompilationUnit parse(char[] unit) {
      ASTParser parser = parse();
      parser.setSource(unit);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   public static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = parse();
      parser.setSource(unit);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   private static void configParser(ASTParser parser) {
      parser.setResolveBindings(true);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setBindingsRecovery(true);
      Map<String, String> options = JavaCore.getOptions();
      options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
      options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
      options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
      parser.setCompilerOptions(options);
   }

   public static boolean contains(ICompilationUnit iUnit, String typeName) {
      boolean rst = false;
      try {
         IType[] types = iUnit.getAllTypes();
         for (IType iType : types) {
            String iTypeName = iType.getElementName();
            if (typeName.equals(iTypeName)) {
               rst = true;
               break;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return rst;
   }

   // CompilationUnit parse(ICompilationUnit unit) {
   // ASTParser parser = ASTParser.newParser(AST.JLS11);
   // parser.setKind(ASTParser.K_COMPILATION_UNIT);
   // parser.setSource(unit);
   // parser.setResolveBindings(true);
   // return (CompilationUnit) parser.createAST(null); // parse
   // }

}
