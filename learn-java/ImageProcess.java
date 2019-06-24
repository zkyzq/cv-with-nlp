public class ImageProcess
{
    public BufferedImage image;
    public double[][] data;

    /**
     * @apiNote 二维数组转置
     * @param matrix
     * @return double[][]
     */
    public static double[][] transpose(double[][] matrix)
    {
        assert (matrix.length!=0 && matrix[0].length!=0);
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] transposedMatrix = new double[n][m];

        for(int x = 0; x < n; x++)
        {
            for(int y = 0; y < m; y++)
            {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }
    /**
     * 
     * @apiNote 冒泡排序
     * @param array
     */
    public static void sort(double[] array)
    {
        if(array.length==0) {
            return;
        }
        for(int i = 0;i<array.length;i++)
        {
            for(int j = array.length-1;j>i;j--)
            {
                if(array[j] < array[j-1])
                {
                    double temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }
    }
    /**
     * @apiNote 中值滤波
     * @param input
     * @return double[][]
     */
    public static double[][] median_filter(final double [][] input)
    {
        assert(input.length!=0 && input[0].length!=0);
        int m = input.length;
        int n = input[0].length;
        double output[][] =new double[m][n];
        double []array = new double[9];
        for(int i = 1;i < m-1;i++)
        {
            for(int j = 1;j < n-1;j++ )
            {
                array[0] = input[i-1][j-1];array[1] = input[i-1][j];array[2] = input[i-1][j+1];
                array[3] = input[i][j-1];array[4] = input[i][j];array[5] = input[i][j+1];
                array[6] = input[i+1][j-1];array[7] = input[i+1][j];array[8] = input[i+1][j+1];
                sort(array);
                output[i][j] = array[4];
            }
        }
        return output;
    }
    /**
     * 
     * @apiNote 手动阈值处理
     * @param outputMap
     * @return
     */
    //阈值处理
   public static double[][] threshold(final double[][] outputMap)
   {
       assert (outputMap.length!=0 && outputMap[0].length!=0);

       double [][] data = outputMap.clone();
       double [][] data2 = transpose(data);
       for(int i = 0;i<data2.length;i++)
       {
           for(int j = 0;j<data2[0].length;j++)
           {
               if(data2[i][j]< 0.5)
               {
                   data2[i][j] = 0;
               }
           }
       }
       return data2;
   }
}