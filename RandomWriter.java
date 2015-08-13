import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that receives a list of files or takes input from System.in and writes
 * a random character sequence of the specified argument length based upon the
 * seed length argument given.
 * 
 * @author 1825794
 */
public class RandomWriter
{
   public static final int VALUE_EOF = -1;

   /**
    * Program that provides a random writing application based upon an integer
    * passed as an argument in args indicating the length of the seed to begin.
    * 
    * Once a seed is chosen, the program will write out a random character from
    * the input that may follow the given seed. The character will then be
    * appended to the seed and the first character of the seed will be dropped.
    * The above will be repeated until the length of the characters is equal to
    * the integer value of the second argument in args.
    * 
    * @param args
    *           1. The seed length. 2. The output length. 3. Input
    *           file(s), at least one.
    */
   public static void main(String[] args)
   {
      final String ERROR_INPUT_TOO_FEW_ARGS = "Not enough arguments provided. Two ints separated by a space must be provided with optional file names to read from.";
      final String ERROR_INPUT_SEED_LENGTH = "You must enter a seed length that is an integer greater than 0";
      final String ERROR_INPUT_OUTPUT_LENGTH = "You must enter an output length that is an integer greater than 0";
      final String ERROR_EXCEPTION_SECURITY = "You do not have permission to access one or more of the requested files.";
      final String ERROR_EXCEPTION_FILE_NOT_FOUND = "One or more of the files can not be found. Please verify that the files are in the correct location";
      final String ERROR_EXCEPTION_FILE_IO = "An error occured while reading the file.";
      final int MIN_LENGTH_FILE_NAME = 2;
      final int MIN_LENGTH_SEED = 1;
      final int MIN_LENGTH_OUTPUT = 1;

      HashMap<String, ArrayList<Character>> seedCharMap = new HashMap<>();
      InputStream inputSource = new BufferedInputStream(System.in);
      int seedLength = 0;
      int outputLength = 0;
      int i;

      // If the user did not provide enough arguments display error message.
      if (args.length < MIN_LENGTH_FILE_NAME)
      {
         System.err.println(ERROR_INPUT_TOO_FEW_ARGS);
         System.exit(1);
      }
      // If we have at least enough arguments.
      else if (args.length >= MIN_LENGTH_FILE_NAME)
      {
         // Validate the seed length given.
         try
         {
            // Try to get the first arg as an integer.
            seedLength = Integer.valueOf(args[0]);

            // Validate that the length is greater than MIN_LENGTH_SEED.
            if (seedLength < MIN_LENGTH_SEED)
            {
               System.err.println(ERROR_INPUT_SEED_LENGTH);
               System.exit(1);
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(ERROR_INPUT_SEED_LENGTH);
            System.exit(1);
         }

         // Now validate the output length given.
         try
         {
            // Try to get the second arg as an integer.
            outputLength = Integer.valueOf(args[1]);

            // Validate that the length is greater than MIN_LENGTH_OUTPUT.
            if (outputLength < MIN_LENGTH_OUTPUT)
            {
               System.err.println(ERROR_INPUT_OUTPUT_LENGTH);
               System.exit(1);
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(ERROR_INPUT_OUTPUT_LENGTH);
            System.exit(1);
         }

         // If we have files to read.
         if (args.length > MIN_LENGTH_FILE_NAME)
         {
            try
            {
               // For each file.
               for (i = MIN_LENGTH_FILE_NAME; i < args.length; i++)
               {
                  // Open the file(s) given.
                  inputSource = new FileInputStream(new File(args[i]));

                  // Update the map of the seeds and the characters that can
                  // follow them.
                  seedCharMap = buildCharMap(seedCharMap, inputSource,
                           seedLength);
               }
            }
            catch (FileNotFoundException e)
            {
               System.err.println(ERROR_EXCEPTION_FILE_NOT_FOUND);
               System.exit(1);
            }
            catch (SecurityException e)
            {
               System.err.println(ERROR_EXCEPTION_SECURITY);
               System.exit(1);
            }
            catch (IOException e)
            {
               System.err.println(ERROR_EXCEPTION_FILE_IO);
               System.exit(1);
            }
         }
         // Since we don't have files to read, we will use the default
         // InputStream.
         else if (args.length == MIN_LENGTH_FILE_NAME)
         {
            try
            {
               // Update the map of the seeds and the characters that can
               // follow them.
               seedCharMap = buildCharMap(seedCharMap, inputSource, seedLength);
            }
            catch (IOException e)
            {
               System.err.println(ERROR_EXCEPTION_FILE_IO);
               System.exit(1);
            }
         }

         // Perform our random writing of length outputLength.
         randomWrite(seedCharMap, outputLength);
      }

   }

   /**
    * Method that accepts a HashMap of seeds with a corresponding ArrayList of
    * characters that have followed the seed in the input.
    * 
    * @param seedCharMap
    *           HashMap of seeds with a corresponding ArrayList of Characters
    *           that have followed the seed in the input.
    * @param outputLength
    *           the number of Characters to output.
    * @throws IOException
    *            if there is an error when writing out.
    */
   private static void randomWrite(
            HashMap<String, ArrayList<Character>> seedCharMap, int outputLength)
   {
      StringBuilder seed = new StringBuilder();
      String[] keys;
      char currentChar;
      int i = 0;

      // Add all of the keys to our keys ArrayList.
      keys = seedCharMap.keySet().toArray(new String[0]);

      // Get the initial random seed.
      seed.append(keys[(int) (Math.random() * keys.length)]);

      // Loop until we have printed outputLength number of characters.
      while (i < outputLength)
      {
         // If the current seed exists in our collection of the seeds.
         if (seedCharMap.get(seed.toString()) != null)
         {
            // Get a random character from this seed's ArrayList of chars that
            // have followed it.
            currentChar = seedCharMap.get(seed.toString()).get(
                     (int) (Math.random() * seedCharMap.get(seed.toString())
                              .size()));

            // If we have a null value for our currentChar.
            if (Integer.valueOf(currentChar) != 0)
            {
               // Update the seed.
               seed.deleteCharAt(0);
               seed.append((char) currentChar);

               // Write out the current char.
               System.out.print(currentChar);

               // Iterate because we have just written out a character.
               i++;
            }
         }
         // Else if the current seed doesn't exist in our collection.
         else
         {
            // Get another random seed.
            seed = new StringBuilder();
            seed.append(keys[(int) (Math.random() * keys.length)]);
         }
      }
   }

   /**
    * Method to add to the current seed and following char map passed as an
    * argument. Using the input source and seed length given in the arguments.
    * 
    * @param seedAndFollowingCharMap
    *           the map of seeds and corresponding characters that can follow.
    * @param inputSource
    *           the source of input we will be reading from
    * @param seedLength
    *           the size of the seeds we will derive from our input source to
    *           store in our map of seeds and corresponding characters.
    * 
    * @return the updated map of seeds and corresponding characters.
    * 
    * @throws IOException
    *            if an error occurs during the reading of the file.
    */
   private static HashMap<String, ArrayList<Character>> buildCharMap(
            HashMap<String, ArrayList<Character>> seedCharMap,
            InputStream inputSource, Integer seedLength) throws IOException
   {
      // Set currentChar to the next character byte.
      int currentChar = inputSource.read();
      StringBuilder currentSeed = new StringBuilder();

      // While we are not at the end of the file.
      while (currentChar != VALUE_EOF && currentSeed.length() < seedLength)
      {
         // Get the first seedLength characters
         // Add currentChar to seed.
         currentSeed.append((char) currentChar);

         // Set currentChar to the next character byte.
         currentChar = inputSource.read();
      }

      // While we are not at the end of the file.
      while (currentChar != VALUE_EOF)
      {
         // If we don't have the current seed in the seedCharMap.
         if (!seedCharMap.containsKey(currentSeed.toString()))
         {
            // Add the current seed to the seedCharMap.
            seedCharMap.put(currentSeed.toString(), new ArrayList<Character>());
         }

         // Get the current seed from the seedCharMap and add currentChar to
         // the returned ArrayList.
         seedCharMap.get(currentSeed.toString()).add(
                  Character.valueOf((char) currentChar));

         // Update the current seed.
         currentSeed = currentSeed.deleteCharAt(0);
         currentSeed = currentSeed.append((char) currentChar);

         // Set currentChar to the next character byte.
         currentChar = inputSource.read();
      }

      return seedCharMap;
   }
}
