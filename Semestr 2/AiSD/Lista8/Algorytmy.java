import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Algorytmy {

    private int [] los;

    public Algorytmy(int n)
    {
        this.los = new int[n];

        for (int i=0; i<los.length; i++)
        {
            Random gen = new Random();
            int el = gen.nextInt(n+1);
            los[i] = el;
        }
    }

    public Integer [] aDistance(int n)
    {
        ArrayList<Integer> distances = new ArrayList<>();
        int l = 0;
        while (l <= n)
        {
            distances.add(l);
            l=3*l+1;
        }
        return distances.toArray(new Integer[distances.size()]);
    }

    public Integer [] bDistance(int n)
    {
        ArrayList<Integer> distances = new ArrayList<>();
        int l = 0;
        for (int i=0; l<=n; i++)
        {
            l = (int)Math.pow(2, i)-1;
            if (l <= n)
                distances.add(l);
        }
        return distances.toArray(new Integer[distances.size()]);
    }

    public Integer [] cDistance(int n)
    {
        ArrayList<Integer> distances = new ArrayList<>();
        int l = 0;
        distances.add(l);
        distances.add(1);
        for (int i = 1; l <= n; i++)
        {
            l = (int)Math.pow(2, i)+1;
            if (l <= n)
                distances.add(l);
        }
        return distances.toArray(new Integer[distances.size()]);
    }

    public Integer [] fiboDistance(int n)
    {
        ArrayList<Integer> distances = new ArrayList<>();
        int l = 1;
        int ppn, pn = 0;
        distances.add(pn);
        for (int i = 1; l <= n; i++)
        {
            ppn = pn;
            pn = l;
            l = ppn + pn;
            if (l <= n)
                distances.add(l);
        }
        return distances.toArray(new Integer[distances.size()]);
    }

    public Integer [] dDistance(int n)
    {
        ArrayList<Integer> distances = new ArrayList<>();
        int l = n/2;
        distances.add(l);
        while (l != 1)
        {
            l *= 0.75;
            distances.add(l);
        }
        distances.add(0);
        Collections.reverse(distances);
        return distances.toArray(new Integer[distances.size()]);
    }

    private int [] utworz(int [] array)
    {
        int [] pom = new int[array.length];
        System.arraycopy(array, 0, pom, 0, array.length);
        return pom;
    }

    private void shellsort1(int [] array, Integer[] distances)
    {
        int n = array.length;
        int iteration = 1;
        int actDistance = distances[distances.length-iteration];
        int comp, cur;

        while (actDistance >= 1)
        {
            for (int i = actDistance; i < n; i++)
            {
                cur = array[i];
                comp = i;
                while (comp >= actDistance && cur < array[comp-actDistance])
                {
                    array[comp] = array[comp-actDistance];
                    comp -= actDistance;
                }
                array[comp] = cur;
            }
            actDistance = distances[distances.length-++iteration];
        }
    }

    public void ShellSort1()
    {
        System.out.println("Wersja 1:");

        int [] pom;
        pom = utworz(los);
        Integer [] dystanse = aDistance(los.length);
        long t1 = System.nanoTime();
        shellsort1(pom, dystanse);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg a: " + t + " ms");

        pom = utworz(los);
        dystanse = bDistance(los.length);
        t1 = System.nanoTime();
        shellsort1(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg b: " + t + " ms");

        pom = utworz(los);
        dystanse = cDistance(los.length);
        t1 = System.nanoTime();
        shellsort1(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg c: " + t + " ms");

        pom = utworz(los);
        dystanse = fiboDistance(los.length);
        t1 = System.nanoTime();
        shellsort1(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg d: " + t + " ms");

        pom = utworz(los);
        dystanse = dDistance(los.length);
        t1 = System.nanoTime();
        shellsort1(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg dodatkowy: " + t + " ms" + "\n");
    }

    private void bubblesort(int [] tabl)
    {
        int size = tabl.length;
        for (int i = 0; i < size-1; i++)
        {
            for (int j = 0; j < size-i-1; j++)
            {
                if (tabl[j] > tabl[j+1])
                    swap(tabl, j+1, j);
            }
        }
    }

    private void swap(int [] tabl, int left, int right)
    {
        int temp = tabl[left];
        tabl[left] = tabl[right];
        tabl[right] = temp;
    }

    private void shellsort2(int [] array, Integer [] distances)
    {
        int n = array.length;
        int iteration = 1;
        int actDistance = distances[distances.length-iteration];
        int comp, cur;

        while (actDistance > 1)
        {
            if (actDistance != 1)
            {
                for (int i=actDistance; i<n; i++)
                {
                    cur = array[i];
                    comp = i;
                    while (comp >= actDistance && cur < array[comp-actDistance])
                    {
                        array[comp] = array[comp-actDistance];
                        comp -= actDistance;
                    }
                    array[comp] = cur;
                }
            }
            iteration++;
            actDistance = distances[distances.length-iteration];
        }
        if (actDistance == 1)
            bubblesort(array);
    }

    public void ShellSort2()
    {
        System.out.println("Wersja 2:");

        int [] pom;
        pom = utworz(los);
        Integer [] dystanse = aDistance(los.length);
        long t1 = System.nanoTime();
        shellsort2(pom, dystanse);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg a: " + t + " ms");

        pom = utworz(los);
        dystanse = bDistance(los.length);
        t1 = System.nanoTime();
        shellsort2(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg b: " + t + " ms");

        pom = utworz(los);
        dystanse = cDistance(los.length);
        t1 = System.nanoTime();
        shellsort2(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg c: " + t + " ms");

        pom = utworz(los);
        dystanse = fiboDistance(los.length);
        t1 = System.nanoTime();
        shellsort2(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg d: " + t + " ms");

        pom = utworz(los);
        dystanse = dDistance(los.length);
        t1 = System.nanoTime();
        shellsort2(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg dodatkowy: " + t + " ms" + "\n");
    }

    private void shellsort3(int [] array, Integer [] distances)
    {
        int n = array.length;
        int iteration = 1;
        int actDistance = distances[distances.length-iteration];
        int comp, cur;

        while (actDistance>1)
        {
            if (actDistance != 1)
            {
                for (int i = actDistance; i < n; i++)
                {
                    cur = array[i];
                    comp = i;
                    while (comp >= actDistance)
                    {
                        if (cur < array[comp - actDistance])
                            swap(array, comp, comp-actDistance);
                        comp -= actDistance;
                    }
                }
            }
            iteration++;
            actDistance = distances[distances.length-iteration];
        }
        if (actDistance==1)
        {
            System.out.println(Arrays.toString(array));
            insertsort(array);
        }
    }

    public void ShellSort3()
    {
        System.out.println("Wersja 3:");

        int [] pom;
        pom = utworz(los);
        Integer [] dystanse = aDistance(los.length);
        long t1 = System.nanoTime();
        shellsort3(pom, dystanse);
        long t2 = System.nanoTime();
        long t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg a: " + t + " ms");

        pom = utworz(los);
        dystanse = bDistance(los.length);
        t1 = System.nanoTime();
        shellsort3(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg b: " + t + " ms");

        pom = utworz(los);
        dystanse = cDistance(los.length);
        t1 = System.nanoTime();
        shellsort3(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg c: " + t + " ms");

        pom = utworz(los);
        dystanse = fiboDistance(los.length);
        t1 = System.nanoTime();
        shellsort3(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg d: " + t + " ms");

        pom = utworz(los);
        dystanse = dDistance(los.length);
        t1 = System.nanoTime();
        shellsort3(pom, dystanse);
        t2 = System.nanoTime();
        t = (t2-t1)/1000000;
        //System.out.println(Arrays.toString(pom));
        System.out.println("Ciąg dodatkowy: " + t + " ms" + "\n");
    }

    private void insertsort(int [] array)
    {
        for (int i = 1; i < array.length; ++i)
        {
            int value = array[i];
            int j;
            int temp;
            for (j = i; (j>0 && value<(temp=array[j-1])); --j)
            {
                array[j] = temp;
            }
            array[j] = value;
        }
    }

    public void wyswietl()
    {
        System.out.println(Arrays.toString(aDistance(100000)));
        System.out.println(Arrays.toString(bDistance(100000)));
        System.out.println(Arrays.toString(cDistance(100000)));
        System.out.println(Arrays.toString(fiboDistance(100000)));
        System.out.println(Arrays.toString(dDistance(100000)));
    }

}