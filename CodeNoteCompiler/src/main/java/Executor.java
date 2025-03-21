import java.io.*;

public class Executor {
    public String executeCode(String language, String code) {
        try {
            switch (language) {
                case "Python":
                    return executePython(code);
                case "Java":
                    return executeJava(code);
                case "C":
                    return executeC(code);
                case "SQL":
                    return executeSQL(code);
                default:
                    return "Unsupported language";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String executePython(String code) throws IOException {
        File scriptFile = new File("script.py");
        try (FileWriter writer = new FileWriter(scriptFile)) {
            writer.write(code);
        }
        ProcessBuilder pb = new ProcessBuilder("python", scriptFile.getAbsolutePath());
        return executeProcess(pb);
    }

    private String executeJava(String code) throws IOException {
        File javaFile = new File("Main.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write(code);
        }
        ProcessBuilder compile = new ProcessBuilder("javac", javaFile.getAbsolutePath());
        ProcessBuilder run = new ProcessBuilder("java", "Main");
        return executeProcess(compile) + executeProcess(run);
    }

    private String executeC(String code) throws IOException {
        File cFile = new File("program.c");
        try (FileWriter writer = new FileWriter(cFile)) {
            writer.write(code);
        }
        ProcessBuilder compile = new ProcessBuilder("gcc", "program.c", "-o", "program");
        ProcessBuilder run = new ProcessBuilder("./program");
        return executeProcess(compile) + executeProcess(run);
    }

    private String executeSQL(String code) throws IOException {
        File sqlFile = new File("query.sql");
        try (FileWriter writer = new FileWriter(sqlFile)) {
            writer.write(code);
        }
        ProcessBuilder pb = new ProcessBuilder("sqlite3", "database.db", "-init", sqlFile.getAbsolutePath());
        return executeProcess(pb);
    }

    private String executeProcess(ProcessBuilder pb) throws IOException {
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }
}
