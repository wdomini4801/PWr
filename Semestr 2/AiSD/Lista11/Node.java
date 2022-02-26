import java.util.ArrayList;

public class Node {

    private String value;
    private ArrayList<Integer> lines;
    private String color;
    private Node left;
    private Node right;
    private Node parent;

    public Node (String value, int n)
    {
        this.value = value;
        this.left = null;
        this.right = null;
        this.lines = new ArrayList<>();
        this.lines.add(n);
        this.color="R";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<Integer> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Integer> lines) {
        this.lines = lines;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void display()
    {
        System.out.print(this.getValue() + " - ");
        for (int i = 0; i < this.getLines().size(); i++)
        {
            if (i == this.getLines().size()-1)
                System.out.print(this.getLines().get(i));
            else
                System.out.print(this.getLines().get(i) + ", ");
        }
        System.out.println();
    }
}
