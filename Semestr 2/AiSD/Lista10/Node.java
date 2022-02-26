public class Node implements Comparable<Node>{

    private String value;
    private int frequency;
    private Node left;
    private Node right;

    public Node(String value, int frequency)
    {
        this.value = value;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public Node(int frequency)
    {
        this.value = null;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeft()
    {
        return left;
    }

    public void setLeft(Node left)
    {
        this.left = left;
    }

    public Node getRight()
    {
        return right;
    }

    public void setRight(Node right)
    {
        this.right = right;
    }

    public int nodes()
    {
        int count = 0;
        count++;
        if (this.getLeft() != null)
            count += this.getLeft().nodes();
        if (this.getRight() != null)
            count += this.getRight().nodes();
        return count;
    }

    public int leaves()
    {
        int count=0;
        if (this.getLeft() == null && this.getRight() == null)
            count++;
        if (this.getLeft() != null)
            count += this.getLeft().leaves();
        if (this.getRight() != null)
            count += this.getLeft().leaves();

        return count;
    }

    public String toString()
    {
        return (this.getValue() + " - " + this.getFrequency());
    }

    @Override
    public int compareTo(Node o)
    {
        return o.getFrequency()-this.getFrequency();
    }
}
