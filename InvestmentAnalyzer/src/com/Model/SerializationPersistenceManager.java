package com.Model;

import java.io.*;

/**
 * Persistance manager based on serialization, the file is hardcoded in the same place as is the application
 * I did not add in-app option to change the path, it would need to be changed here in the code
 */

public class SerializationPersistenceManager implements PersistenceManager {

    private String fileName;

    public SerializationPersistenceManager(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void saveAll(Evidence evidence) {

            try {
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                for (InvestmentPlan p : evidence.getInvestmentPlans()) {
                    out.writeObject(p);
                }
                out.flush();
                out.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public EvidenceImpl loadAll() {
        EvidenceImpl evidence = new EvidenceImpl();
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            boolean flag = true;
            while (flag) {
                InvestmentPlan p = (InvestmentPlan) in.readObject();
                if (p != null) {
                    evidence.addPlan(p);
                } else {
                    flag = false;
                }
            }

        } catch (EOFException eoef) {
            return evidence;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return evidence;
    }

    public String getFileName() {
        return fileName;
    }
}
