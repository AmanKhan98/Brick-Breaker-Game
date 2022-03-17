import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame jframe = new JFrame();
        Gameplay gameplay = new Gameplay();
        jframe.setBounds(10, 10, 710, 600);
        jframe.setTitle("Brick Breaker");
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gameplay);
    }
}
