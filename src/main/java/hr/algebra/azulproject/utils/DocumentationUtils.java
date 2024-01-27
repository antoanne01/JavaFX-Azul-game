package hr.algebra.azulproject.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DocumentationUtils {

    public static void handleDocumentation(Path targetPath){
        try (Stream<Path> paths = Files.walk(targetPath)) {
            List<String> classFiles = paths
                    // List all classes, then filter to take only ones which end up with .class
                    .map(Path::toString)
                    .filter(file -> file.endsWith(".class"))
                    .toList();

            String headerHtml = """
                     <!DOCTYPE html>
                        <html>
                        <head>
                        <title>HTML Tutorial</title>
                        </head>
                        <body>
                        
                        """;

            for(String classFile : classFiles){
                String[] classFileToken = classFile.split("classes");
                String classFilePath = classFileToken[1];
                String reducedClassFilePath = classFilePath.substring(1, classFilePath.lastIndexOf('.'));

                String fullyQualifiedName = reducedClassFilePath.replace('/', '.');

                headerHtml += "<h2>" + fullyQualifiedName + "</h2>";

                Class<?> deserializedClass = Class.forName(fullyQualifiedName);

                Field[] classFields = deserializedClass.getDeclaredFields();

                for(Field field : classFields){
                    headerHtml += "<h3>";
                    int modifiers = field.getModifiers();

                    if(Modifier.isPublic(modifiers)){
                        headerHtml += "public ";
                    }
                    else if (Modifier.isPrivate(modifiers)) {
                        headerHtml += "private ";
                    }
                    else if (Modifier.isProtected(modifiers)) {
                        headerHtml += "protected ";
                    }

                    if(Modifier.isStatic(modifiers)){
                        headerHtml += "static ";
                    }
                    if (Modifier.isFinal(modifiers)) {
                        headerHtml += "final ";
                    }

                    headerHtml += field.getType().getTypeName() + " ";
                    headerHtml += field.getName() + "\n";

                    headerHtml += "</h3>";
                }

                 //Extracting constructos bellow

                Constructor[] constructors = deserializedClass.getDeclaredConstructors();
                for(Constructor<?> constructor : constructors){

                    headerHtml += "CONSTRUCTOR";
                    headerHtml += "<h3>";
                    int modifiers = constructor.getModifiers();

                    if (Modifier.isPublic(modifiers)) {
                        headerHtml += "public ";
                    } else if (Modifier.isPrivate(modifiers)) {
                        headerHtml += "private ";
                    } else if (Modifier.isProtected(modifiers)) {
                        headerHtml += "protected ";
                    }

                    headerHtml += fullyQualifiedName + " ";

                    Class<?>[] paramTypes = constructor.getParameterTypes();
                    headerHtml += "(";
                    for (int i = 0; i < paramTypes.length; i++) {
                        headerHtml += paramTypes[i].getSimpleName();
                        if (i < paramTypes.length - 1) {
                            headerHtml += ", ";
                        }
                    }
                    headerHtml += ")";

                    headerHtml += "\n";
                    headerHtml += "</h3>";
                }

                Method[] methods = deserializedClass.getDeclaredMethods();
                for(Method method : methods){
                    String methodName = method.getName();

                    Class<?>[] paramTypes = method.getParameterTypes();

                    headerHtml += "METHOD";
                    headerHtml += "<h3>";

                    int modifiers = method.getModifiers();

                    if (Modifier.isPublic(modifiers)) {
                        headerHtml += "public ";
                    } else if (Modifier.isPrivate(modifiers)) {
                        headerHtml += "private ";
                    } else if (Modifier.isProtected(modifiers)) {
                        headerHtml += "protected ";
                    }
                    // Method Name
                    headerHtml += method.getReturnType().getSimpleName() + " " + methodName + "(";

                    for (int i = 0; i < paramTypes.length; i++) {
                        headerHtml += paramTypes[i].getSimpleName();
                        if (i < paramTypes.length - 1) {
                            System.out.print(", ");
                        }
                    }

                    headerHtml += ")";
                    headerHtml += "</h3>";
                }
            }

            String footerHtml = """
                        </body>
                        </html>
                    """;

            Path documentationFilePath = Path.of("files/documentation.html");

            String fullHtml = headerHtml + footerHtml;

            Files.write(documentationFilePath, fullHtml.getBytes());
            DialogUtils.savedDocumentation();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
