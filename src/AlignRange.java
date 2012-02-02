//Represents two corresponding ranges

public class AlignRange
{
    public AlignRange(int q1, int q2, int s1, int s2)
    {
        qStart = q1;
		qEnd = q2;
		sStart = s1;
		sEnd = s2;
    }
    
    public int qStart, qEnd, sStart, sEnd;
}