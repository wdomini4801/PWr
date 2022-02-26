import java.util.AbstractList;
import java.util.Iterator;

public class OneWayLinkedListWithHead<T> extends AbstractList<T> {

    public class Element {

        private T value;
        private Element next;

        public T getValue() {
            return value;}

        public void setValue(T value) {
            this.value = value;}

        public Element getNext() {
            return next;}

        public void setNext(Element next) {
            this.next = next;}

        Element(T data)
        {
            this.value=data;
        }
    }

    Element head = null;

    public OneWayLinkedListWithHead(){}

    public boolean isEmpty()
    {
        return head==null;
    }

    @Override
    public void clear()
    {
        head=null;
    }

    @Override
    public int size()
    {
        int pos=0;
        Element actElem=head;
        while(actElem!=null)
        {
            pos++;
            actElem=actElem.getNext();
        }
        return pos;
    }

    private Element getElement(int index)
    {
        Element actElem=head;
        while(index>0 && actElem!=null)
        {
            index--;
            actElem=actElem.getNext();
        }
        return actElem;
    }

    @Override
    public boolean add(T e)
    {
        Element newElem=new Element(e);

        if(head==null)
        {
            head=newElem;
            return true;
        }

        Element tail=head;

        while(tail.getNext()!=null)
            tail=tail.getNext();
        tail.setNext(newElem);
        return true;
    }

    @Override
    public void add(int index, T data)
    {
        Element newElem=new Element(data);

        if(index==0)
        {
            newElem.setNext(head);
            head=newElem;
        }
        else
        {
            Element actElem=getElement(index-1);

            if(actElem!=null)
            {
                newElem.setNext(actElem.getNext());
                actElem.setNext(newElem);
            }
        }
    }

    @Override
    public T get(int index)
    {
        Element actElem=getElement(index);
        if (actElem==null)
        {
            return null;
        }
        else
        {
            return actElem.getValue();
        }
    }

    @Override
    public T remove(int index)
    {
        if(head==null)
            return null;

        if(index==0)
        {
            T retValue=head.getValue();
            head=head.getNext();
            return retValue;
        }

        Element actElem=getElement(index-1);

        if(actElem==null || actElem.getNext()==null)
            return null;

        T retValue=actElem.getNext().getValue();
        actElem.setNext(actElem.getNext().getNext());
        return retValue;
    }

    public boolean remove(Object value)
    {
        if(head==null)
            return false;
        if(head.getValue().equals(value))
        {
            head=head.getNext();
            return true;
        }

        Element actElem=head;

        while(actElem.getNext()!=null && !actElem.getNext().getValue().equals(value))
            actElem=actElem.getNext();

        if(actElem.getNext()==null)
            return false;

        actElem.setNext(actElem.getNext().getNext());
        return true;
    }

    private class InnerIterator implements Iterator<T>{

        Element actElem;
        public InnerIterator()
        {
            actElem=head;
        }

        @Override
        public boolean hasNext()
        {
            return actElem!=null;
        }

        @Override
        public T next()
        {
            T value=actElem.getValue();
            actElem=actElem.getNext();
            return value;
        }

    }
    @Override
    public Iterator<T> iterator()
    {
        return new InnerIterator();
    }

}
