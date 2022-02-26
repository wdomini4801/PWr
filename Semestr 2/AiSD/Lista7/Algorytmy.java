import java.util.Arrays;
import java.util.Random;

public class Algorytmy {

    private int [] los;
    private int [] rosn;
    private int [] malej;

    private int [] odwrTabl(int [] tabl)
    {
        int [] rob = new int[tabl.length];
        System.arraycopy(tabl, 0, rob, 0, tabl.length);
        for(int i=0; i<rob.length/2; i++)
        {
            int temp = rob[i];
            rob[i] = rob[rob.length -i -1];
            rob[rob.length -i -1] = temp;
        }
        return rob;
    }

    public Algorytmy(int n)
    {
        this.los = new int[n];
        this.rosn = new int[los.length];
        this.malej = new int[los.length];

        for (int i=0; i<los.length; i++)
        {
            Random gen = new Random();
            int el = gen.nextInt(n+1);
            los[i] = el;
            rosn[i] = el;
            malej[i] = el;
        }
        Arrays.sort(rosn);
        malej=odwrTabl(rosn);
    }

    public void wyswietl()
    {
        System.out.println("Ciąg losowy: " + Arrays.toString(los));
        System.out.println("Ciąg posortowany rosnąco: " + Arrays.toString(rosn));
        System.out.println("Ciąg posortowany malejąco: " + Arrays.toString(malej));
    }

    private int [] utworz(int [] array)
    {
        int [] pom = new int[array.length];
        System.arraycopy(array, 0, pom, 0, array.length);
        return pom;
    }

    //BubbleSort

    private void bubblesort(int [] array)
    {
        int size = array.length;
        for (int i=1; i<size; ++i)
        {
            for (int l=0; l<(size-i); ++l)
            {
                int r = l+1;
                if (array[l]>array[r])
                    swap(array, l, r);
            }
        }
        //System.out.println(Arrays.toString(array));
    }

    public void BubbleSort()
    {
        System.out.println("Algorytm BubbleSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        bubblesort(pom);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");

        pom = utworz(rosn);
        t1 = System.nanoTime();
        bubblesort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t+ " ms");

        pom = utworz(malej);
        t1 = System.nanoTime();
        bubblesort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
    }

    private void swap(int [] tabl, int left, int right)
    {
        if (left!=right)
        {
            int temp = tabl[left];
            tabl[left] = tabl[right];
            tabl[right] = temp;
        }
    }

    //InsertionSort

    private void insertsort(int [] array)
    {
        for (int i=1; i<array.length; ++i)
        {
            int value = array[i];
            int j;
            int temp;

            for (j=i; (j>0 && value<(temp=array[j-1])); --j)
                array[j]=temp;

            array[j]=value;
        }
        //System.out.println(Arrays.toString(array));
    }

    public void InsertSort()
    {
        System.out.println("Algorytm InsertSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        insertsort(pom);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");

        pom = utworz(rosn);
        t1 = System.nanoTime();
        insertsort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t + " ms");

        pom = utworz(malej);
        t1 = System.nanoTime();
        insertsort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
    }

    //SelectSort

    private void selectsort(int [] array)
    {
        int size = array.length;
        for (int slot=0; slot<size-1; ++slot)
        {
            int najm = slot;
            for (int check=slot+1; check<size; ++check)
            {
                if (array[check] < array[najm])
                    najm=check;
            }
            swap(array, najm, slot);
        }
    }

    public void SelectSort()
    {
        System.out.println("Algorytm SelectSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        selectsort(pom);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");

        pom = utworz(rosn);
        t1 = System.nanoTime();
        selectsort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t + " ms");

        pom = utworz(malej);
        t1 = System.nanoTime();
        selectsort(pom);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
    }

    //MergeSort

    private void mergesort(int [] array, int l, int r)
    {
        if (l<r)
        {
            int m = (l+r)/2;
            mergesort(array, l, m);
            mergesort(array, m+1, r);
            merge(array, l, m, r);
        }

    }

    private void merge(int [] array, int l, int m, int r)
    {
        int n1 = m-l+1;
        int n2 = r-m;
        int [] left = new int [n1];
        int [] right = new int [n2];

        System.arraycopy(array, l, left, 0, n1);
        for (int j=0; j<n2; ++j)
        {
            right[j] = array[m+1+j];
        }

        int i=0, j=0;
        int k=l;

        while (i < n1 && j < n2)
        {
            if (left[i] <= right[j])
            {
                array[k] = left[i];
                i++;
            }
            else
            {
                array[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            array[k] = left[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    public void MergeSort()
    {
        System.out.println("Algorytm MergeSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        mergesort(pom, 0, pom.length-1);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");

        //System.out.println(Arrays.toString(pom));
        pom = utworz(rosn);
        t1 = System.nanoTime();
        mergesort(pom, 0, pom.length-1);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t + " ms");
        //System.out.println(Arrays.toString(pom));

        pom = utworz(malej);
        t1 = System.nanoTime();
        mergesort(pom, 0, pom.length-1);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
        //System.out.println(Arrays.toString(pom));
    }

    //HeapSort

    private void sink(int [] heap, int index, int n)
    {
        int indexNajw = 2*index+1;
        if (indexNajw < n)
        {
            if (indexNajw+1 < n && heap[indexNajw] < heap[indexNajw+1])
                indexNajw++;
            if (heap[index] < heap[indexNajw])
            {
                swap(heap, index, indexNajw);
                sink(heap, indexNajw, n);
            }
        }
    }

    private void heapAdjustment(int [] heap, int n)
    {
        for (int i=(n-1)/2; i>=0; i--)
            sink(heap, i, n);
    }

    private void heapsort(int [] array, int n)
    {
        heapAdjustment(array, n);
        for (int i = n-1; i>0; i--)
        {
            swap(array, i, 0);
            sink(array, 0, i);
        }
    }

    public void HeapSort()
    {
        System.out.println("Algorytm HeapSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        heapsort(pom, pom.length);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");
        //System.out.println(Arrays.toString(pom));

        pom = utworz(rosn);
        t1 = System.nanoTime();
        heapsort(pom, pom.length);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t + " ms");
        //System.out.println(Arrays.toString(pom));

        pom = utworz(malej);
        t1 = System.nanoTime();
        heapsort(pom, pom.length);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
        //System.out.println(Arrays.toString(pom));
    }

    //QuickSort

    private int partition(int [] array, int from, int to)
    {
        Random random = new Random();
        int rand = from+random.nextInt(to-from);
        swap(array, from, rand);
        int value = array[from];
        int indexBigger = from+1, indexLower = to-1;
        do {
            while(indexBigger <= indexLower && array[indexBigger] <= value)
                indexBigger++;
            while(array[indexLower] > value)
                indexLower--;
            if(indexBigger < indexLower)
                swap(array,indexBigger,indexLower);
        } while (indexBigger < indexLower);
        swap(array, indexLower, from);
        return indexLower;
    }

    private void quicksort(int [] array, int start, int end)
    {
        if (end-start > 1)
        {
            int partition = partition(array, start, end);
            quicksort(array, start, partition);
            quicksort(array, partition+1, end);
        }
    }

    public void QuickSort()
    {
        System.out.println("Algorytm QuickSort: ");

        int [] pom = utworz(los);
        long t1 = System.nanoTime();
        quicksort(pom, 0, pom.length);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        System.out.println("Dane losowe: " + t + " ms");
        //System.out.println(Arrays.toString(pom));

        pom = utworz(rosn);
        t1 = System.nanoTime();
        quicksort(pom, 0, pom.length);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane rosnąco: " + t + " ms");
        //System.out.println(Arrays.toString(pom));

        pom = utworz(malej);
        t1 = System.nanoTime();
        quicksort(pom, 0, pom.length);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        System.out.println("Dane posortowane malejąco: " + t + " ms" + "\n");
        //System.out.println(Arrays.toString(pom));
    }

}
