package Lista5;

import java.util.AbstractList;

public class OneWayLinkedListWithHead<Integer> extends AbstractList<Integer> {

    public class Element {

        private Integer value;
        private Element next;

        public Integer getValue() {
            return value;}

        public void setValue(Integer value) {
            this.value = value;}

        public Element getNext() {
            return next;}

        public void setNext(Element next) {
            this.next = next;}

        Element(Integer data)
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
    public boolean add(Integer e)
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
    public void add(int index, Integer data)
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
    public Integer get(int index)
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
    public Integer remove(int index)
    {
        if(head==null)
            return null;

        if(index==0)
        {
            Integer retValue=head.getValue();
            head=head.getNext();
            return retValue;
        }

        Element actElem=getElement(index-1);

        if(actElem==null || actElem.getNext()==null)
            return null;

        Integer retValue=actElem.getNext().getValue();
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
}
