import java.util.ArrayList;

abstract public class BLAST
{
    protected int scoreCutoff;
    protected int wordLength;
    protected int gapOpenPenalty;
    protected int gapExtensionPenalty;
    
    //returns the score for any two letter pairs
    protected abstract int getScore(char a, char b);
    
    //creates the list of words to be used in the initial ungapped alignment
    protected abstract int[] findSeeds(String query);
    
    protected void align(String subject, String query)
    {
        //seeds is an array of indexes into the query representing the words
        int[] seeds = findSeeds(query);
        ArrayList hits = new ArrayList();
		ArrayList alignments = new ArrayList();
        int alignscore = 0;
        
        int startRange, endRange;
        
        //1: find all exact matches between a word and some position in the query
        int pos = 0;
        for(int i = 0; i < seeds.length; i++)
        {
            pos = subject.indexOf(query.substring(seeds[i],seeds[i] + (wordLength-1)),pos);
            while(pos != -1)
            {
                //HSP(matching word index, position in query)
                hits.add(new HSP(i,pos));
                pos = subject.indexOf(query.substring(seeds[i],seeds[i] + (wordLength-1)),pos+1);
            }
			pos = 0;
        }
        
        //2: extend the match forwards and backwards until the score decreases
        for(int i = 0; i < hits.size(); i++)
        {
			HSP temp = (HSP)hits.get(i);
            int currScore = 0;
            //extend forward
            for(endRange = 0; endRange < subject.length() - wordLength && endRange < query.length() - wordLength; endRange++)
            {
                //stop extending if we get a negative score
				currScore = getScore(query.charAt(endRange+(temp.qPos + wordLength)),subject.charAt(endRange+(temp.sPos + wordLength)))
                if(currScore < 0)
                    break;
                alignscore += currScore;
            }
            
            //extend backwards
            for(startRange = 1; startRange < temp.qPos && startRange < temp.sPos; startRange++)
			{
                currScore = getScore(query.charAt(temp.qPos - startRange),subject.charAt(temp.sPos - startRange))
				if(currScore < 0)
					break;
                alignscore += currScore;
			}
			
			//3: keep only the extended alignments that pass cutoff
            if(alignscore > scoreCutoff)
                //query start, query end, subject start, subject end
                alignments.add(new AlignRange(temp.qPos - startRange,temp.qPos + endRange,temp.sPos - startRange,temp.sPos + endRange));
        }

        
        //4: still working
    }
                             
}
		
		