package com.example.demo_interpr;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static java.lang.Class.forName;

public class DemoInterprApplication {
    static {

generate();
    }

    public static void main(String[] args) {
        method();
    }

    public static void method() {
        File root = new File("C:\\Users\\ababus\\IdeaProjects\\demo_interpr\\src\\Main\\java\\com\\example\\demo_interpr\\generated");
        try {
            // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            Class<?> cls = forName("com.example.demo_interpr.generated.MyClass", true, classLoader);
            Object instance = cls.newInstance();

            Method[] allMethods = cls.getDeclaredMethods();
            for (Method m : allMethods) {
                String mname = m.getName();
                if (!mname.startsWith("test")
                        || (m.getGenericReturnType() != boolean.class)) {
                    continue;
                }
                Type[] pType = m.getGenericParameterTypes();

                System.out.format("invoking %s()%n", mname);

                    m.setAccessible(true);
                    Object o = m.invoke(instance);
                    System.out.format("%s() returned %b%n", mname, (Boolean) o);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static File generate() {
        String source = codeString();
        File root = new File("C:\\Users\\ababus\\IdeaProjects\\demo_interpr\\src\\Main\\java\\com\\example\\demo_interpr\\generated");
        try {

            File sourceFile = new File(root, "MyClass.java");
            sourceFile.getParentFile().mkdirs();
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());

            // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            Class<?> cls = forName("com.example.demo_interpr.generated.MyClass", true, classLoader);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return root;

    }


    //What we should generate
    private static String codeString() {
        return "package com.example.demo_interpr.generated;\n" +
                "\n" +
                "import com.example.demo_interpr.domain.Privilege;\n" +
                "import com.example.demo_interpr.domain.User;\n" +
                "\n" +
                "import java.util.Arrays;\n" +
                "import java.util.Collections;\n" +
                "import java.util.Comparator;\n" +
                "import java.util.List;\n" +
                "\n" +
                "import static java.util.Arrays.asList;\n" +
                "\n" +
                "public class MyClass {\n" +
                "\n" +
                "    \n" +
                "\n" +
                "    private static final List<Privilege> ALL_PRIVILEGES = asList(Privilege.values());\n" +
                "\n" +
                "    private static List<User> sortByAgeDescAndNameAsc(final List<User> users) {\n" +
                "\n" +
                "        List<User> listOfUsers = users;\n" +
                "        //First implementation\n" +
                "        Collections.sort(listOfUsers, Comparator.comparing(User::getFirstName));\n" +
                "        Collections.sort(listOfUsers, Comparator.comparingInt(User::getAge).reversed());\n" +
                "\n" +
                "        return users;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    private boolean testShouldSortUsersByAgeDescAndNameDesc() {\n" +
                "        final User user1 = new User(1L, \"John\", \"Doe\", 26, ALL_PRIVILEGES);\n" +
                "        final User user2 = new User(2L, \"Greg\", \"Smith\", 30, ALL_PRIVILEGES);\n" +
                "        final User user4 = new User(2L, \"AGreg\", \"Smith\", 30, ALL_PRIVILEGES);\n" +
                "        final User user3 = new User(3L, \"Alex\", \"Smith\", 13, ALL_PRIVILEGES);\n" +
                "\n" +
                "        final List<User> sortedUsers =\n" +
                "                sortByAgeDescAndNameAsc(asList(user1, user2, user3, user4));\n" +
                "\n" +
                "        return (sortedUsers).equals(asList(user4, user2, user1, user3));\n" +
                "    }\n" +
                "\n" +
                "    private boolean testBool(){\n" +
                "        return false;\n" +
                "    }\n" +
                "}";
    }
}
