import java.util.Comparator;

public class ComparatorFrequency implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2)
    {
        if (o1.getFrequency()!=o2.getFrequency())
            return o1.getFrequency()-o2.getFrequency();
        else
            return o1.nodes()-o2.nodes();
    }
}
