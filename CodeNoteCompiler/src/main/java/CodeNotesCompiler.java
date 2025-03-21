import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodeNotesCompiler extends JFrame {
    private JTextArea notesArea;
    private JTextArea codeArea;
    private JTextArea outputArea;
    private JComboBox<String> languageSelector;
    private JButton runButton;

    public CodeNotesCompiler() {
        setTitle("Code Notes Compiler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Notes Section - Takes 50% of the space
        notesArea = new JTextArea();
        notesArea.setBorder(BorderFactory.createTitledBorder("Notes"));
        notesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setPreferredSize(new Dimension(800, 300)); // Half of the window height
        add(notesScroll, BorderLayout.NORTH);

        // Code and Output Sections - Share the remaining 50%
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        
        codeArea = new JTextArea();
        codeArea.setBorder(BorderFactory.createTitledBorder("Write Your Code"));
        codeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        centerPanel.add(new JScrollPane(codeArea));
        
        outputArea = new JTextArea();
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        outputArea.setEditable(false);
        centerPanel.add(new JScrollPane(outputArea));
        
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel (Language Selector & Run Button)
        JPanel bottomPanel = new JPanel();
        String[] languages = {"Python", "Java", "C", "SQL"};
        languageSelector = new JComboBox<>(languages);
        runButton = new JButton("Run Code");

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCode();
            }
        });

        bottomPanel.add(new JLabel("Select Language:"));
        bottomPanel.add(languageSelector);
        bottomPanel.add(runButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void runCode() {
        String code = codeArea.getText();
        String language = (String) languageSelector.getSelectedItem();

        if (code.isEmpty()) {
            outputArea.setText("No code to execute.");
            return;
        }

        Executor executor = new Executor();
        String result = executor.executeCode(language, code);
        outputArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CodeNotesCompiler().setVisible(true));
    }
}
