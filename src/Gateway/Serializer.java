package Gateway;
import Controllers.*;

import java.io.*;


public class Serializer {

    /**
     *
     * @return A use case storage that was recovered
     */
    public UseCaseStorage Read() {

        try{
            FileInputStream fileIn = new FileInputStream("phase2/src/Storage/ucs2.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            UseCaseStorage ucs = (UseCaseStorage) in.readObject();
            in.close();
            fileIn.close();
            return ucs;
        } catch (FileNotFoundException e) {
            System.out.println("Serializable file wasn't found, check the path.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Object was not reconstructable, initialized a fresh system: ");
            System.out.println(e);
            UseCaseFactory ucf = new UseCaseFactory();
            UseCaseStorage ucs = ucf.getUseCaseStorage();
            return ucs;
        } catch (Exception e){
            System.out.println(e);
            System.out.println("System failed to reboot from nmemmhorhee.");
        }
        // make a new one instead? not too sure serializing works
        return new UseCaseFactory().getUseCaseStorage();
    }

    /**
     *
     * @param ucs the use case storage to be updated
     * @return the writing of the UCS
     */
    public Boolean Write(UseCaseStorage ucs){
        try{
//            UseCaseStorage ucs = new UseCaseStorage(tm,rm,sm,am,im);
            FileOutputStream fileOut = new FileOutputStream("phase2/src/Storage/ucs2.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ucs);
            out.close();
            fileOut.close();
            System.out.println("Serialized current state to ucs2.ser");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    }
