package wethinkcode.utils;

import java.io.File;
import java.util.Scanner;
import wethinkcode.actions.CreateDatabase;
import wethinkcode.actions.CreateTable;
import wethinkcode.actions.Delete;
import wethinkcode.actions.Insert;
import wethinkcode.actions.Select;
import wethinkcode.actions.Update;

public class BasicPrompt
{
    static Scanner objScan = new Scanner(System.in);
    private String _strChoice;
    private String _strQuery;
    private String _strTable;
    private String _strColumn;
    private String _name;
    private int _id;

    public static boolean checkIfDatabaseExists()
    {
        File file = new File(DatabaseHelper.DATABASEFILE_STRING);
        if (file.exists())
        {
            return true;
        }
        return false;

    }

    public void AppendOnDatabase()
    {
        int choice;

        // Console.consoleOutput("Database already exists. do you wish to append to existing data:");
        Console.consoleOutput("1. Insert");
        Console.consoleOutput("2. Update");
        Console.consoleOutput("3. Delete");
        Console.consoleOutput("4. ViewAll(Select)");
        Console.consoleOutput("5. Exit");
        choice = objScan.nextInt();

        switch (choice) {
            case 1:
                setQuery("Insert");
                selectTable();
                break;
            case 2:
                setQuery("Update");
                selectTable();
                break;
            case 3:
                setQuery("Delete");
                selectTable();
                break;
            case 4:
                setQuery("View");
                selectTable();
                break;
            case 5:
                System.exit(1);
                break;
            default:
                AppendOnDatabase();
                break;
        }

    }
    public void databaseLogic()
    {
        int choice;
        if (checkIfDatabaseExists() == true)
        {
            AppendOnDatabase();
        }
        else
        {
            Console.consoleOutput("1. " + "Create database.");
            choice = objScan.nextInt();

            switch (choice) {
                case 1:
                    CreateDatabase.createNewDatabase();
                    tableLogic();
                    break;
                default:
                    databaseLogic();
            }
        }
    }

    public void tableLogic()
    {
        int choice;
        Console.consoleOutput("1. " + "Create tables. Market and Broker");
        choice = objScan.nextInt();
        switch (choice) {
            case 1:
                CreateTable.createMarketModelTable();
                CreateTable.createBrokerModelTable();
                AppendOnDatabase();
                break;
            default:
                tableLogic();
                break;
        }
    }

    public void selectTable()
    {
        try
        {
            int choice;
            Console.consoleOutput("\n");
            Console.consoleOutput(getQuery() + " table.");
            Console.consoleOutput("1. MarketModel table");
            Console.consoleOutput("2. BrokerModel table");
            choice = objScan.nextInt();

            if (choice < 1 || choice > 2)
            {
                selectTable();
            }
            else if (choice == 1)
            {
                setClientString("Market");
                setTableName("MarketModel");
                setColumnName("MarketName");
                if (getQuery().equalsIgnoreCase("Insert"))
                {
                    setName();
                }
                else
                {
                    queryLogic();
                }
            }
            else if (choice == 2)
            {
                setClientString("Broker");
                setTableName("BrokerModel");
                setColumnName("BrokerName");
                if (getQuery().equalsIgnoreCase("Insert"))
                {
                    setName();
                }
                else
                {
                    queryLogic();
                }
            }
        }catch(Exception e)
        {
            selectTable();
        }
    }

    public void  queryLogic()
    {
        // selectTable();

        if (getQuery().equalsIgnoreCase("Insert"))
        {
            Insert.insert(getTableName(), getColumnName(), getName());
            AppendOnDatabase();
        }
        if (getQuery().equalsIgnoreCase("Update"))
        {
            selectId();
            Update.update(getTableName(), getId(), getColumnName(), getName());
            AppendOnDatabase();
        }
        if (getQuery().equalsIgnoreCase("Delete"))
        {
            selectId();
            Delete.delete(getTableName(), getId());
            AppendOnDatabase();
        }
        if (getQuery().equalsIgnoreCase("View"))
        {
            Select.selectAll(getTableName(), getColumnName());
            AppendOnDatabase();
        }
    }


    public void selectId()
    {
        Console.consoleOutput(getQuery() + ".. Select Id:");
        Select.selectId(getTableName(), getColumnName());
        setId(objScan.nextInt());

        if (getQuery().equalsIgnoreCase("Update"))
        {
            String nameOfClient;

            Console.consoleOutput("Enter name of " + getClientString() + " :");
            nameOfClient = objScan.next();

            this._name = nameOfClient;
        }
    }

    public void setName()
    {
        String nameOfClient;

        Console.consoleOutput("Enter name of " + getClientString() + " :");
        nameOfClient = objScan.next();

        this._name = nameOfClient;
        queryLogic();
    }

    public String getName()
    {
        return this._name;
    }

    public void setClientString(String clientName)
    {
        this._strChoice = clientName;
    }

    public String getClientString()
    {
        return this._strChoice;
    }

    public void setQuery(String strQuery)
    {
        this._strQuery = strQuery;
    }

    public String getQuery()
    {
        return this._strQuery;
    }

    public void setColumnName(String strColumn)
    {
        this._strColumn = strColumn;
    }

    public String getColumnName()
    {
        return this._strColumn;
    }

    public void setTableName(String strTable)
    {
        this._strTable = strTable;
    }

    public String getTableName()
    {
        return this._strTable;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public int getId()
    {
        return this._id;
    }
}