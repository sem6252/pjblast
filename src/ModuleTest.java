public class ModuleTest extends BLASTP
{
    public static void main(String[] args)
    {
        BLASTP aligner = new BLASTP();
        String seq1 = "MAETFWKV";
        String seq2 = "MAEWFSPL";
        
        int[] seeds= aligner.findSeeds(seq1);
        System.out.println("Seed indexes for seq1");
        for(int i = 0; i < seeds.length; i++)
        {
            System.out.print(seeds[i] + " ");
        }
        System.out.println();
    }
}