package Lista6;

import java.util.AbstractList;

public class OneWayLinkedListWithHeadTail<T> extends AbstractList<T> {

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
    Element tail = null;

    public OneWayLinkedListWithHeadTail () {}

    public boolean isEmpty()
    {
        return (head==null && tail==null);
    }

    @Override
    public void clear()
    {
        head=null;
        tail=null;
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

        if(head==null && tail==null)
        {
            head=newElem;
            tail=newElem;
            return true;
        }

        tail.setNext(newElem);
        tail=newElem;
        return true;
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
        if(head==null && tail==null)
            return null;

        if(index==0 && head==tail)
        {
            T retValue=head.getValue();
            head=head.getNext();
            tail=null;
            return retValue;
        }

        if (index==0 && head!=tail)
        {
            T retValue=head.getValue();
            head=head.getNext();
            return retValue;
        }

        Element actElem=getElement(index-1);

        if(actElem==null || actElem.getNext()==null)
            return null;

        if (actElem.getNext()==tail)
        {
            T retvalue = actElem.getNext().getValue();
            actElem.setNext(null);
            tail=actElem;
            return retvalue;
        }

        T retValue=actElem.getNext().getValue();
        actElem.setNext(actElem.getNext().getNext());
        return retValue;
    }
}
