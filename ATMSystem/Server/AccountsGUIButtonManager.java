/**
 * Associated with the AccountGUI this class is used to enable and disable
 * particular navigation buttons depending on record status. For instance, when a 
 * record is being edited, the next and previous account record pointers will
 * be disabled
 * @author Jamie Brindle (06352322)
 */
public class AccountsGUIButtonManager {
	
    private AccountsGUI accountsGUI;
	
    /**
     * AccountGUIButtonManager constructor taking in the AccountGUI class
     * as a parameter thus directly affecting it
     * @param GUI associating the AccountsGUIButtonManager with the AccountsGUI class
     */
    public AccountsGUIButtonManager(AccountsGUI GUI) {
        this.accountsGUI = GUI;
	      
    } 
	
    /**
     * Sets all navigation buttons enabled or disabled
     * @param b - if false then disable, enable otherwise
     */
    public void setNavButtonsEnabled(boolean b) {
        if (b == false) {
            accountsGUI.firstNavButton.setEnabled(false);
            accountsGUI.lastNavButton.setEnabled(false); 
            accountsGUI.nextNavButton.setEnabled(false); 
            accountsGUI.previousNavButton.setEnabled(false);
            accountsGUI.newRecordNavButton.setEnabled(false); 
            accountsGUI.saveNavButton.setEnabled(false); 
            accountsGUI.editNavButton.setEnabled(false); 
            accountsGUI.removeNavButton.setEnabled(false);
            accountsGUI.searchBarSearchButton.setEnabled(false);
        } else {
            accountsGUI.firstNavButton.setEnabled(true);
            accountsGUI.lastNavButton.setEnabled(true); 
            accountsGUI.nextNavButton.setEnabled(true); 
            accountsGUI.previousNavButton.setEnabled(true);
            accountsGUI.newRecordNavButton.setEnabled(true); 
            accountsGUI.editNavButton.setEnabled(true); 
            accountsGUI.removeNavButton.setEnabled(true);
            accountsGUI.searchBarSearchButton.setEnabled(true);
    		
        }
    }
    
    /**
     * Sets navigation buttons into edit mode, i.e. no next and previous
     * account record pointers
     * @param b - if false then disable, enable otherwise
     */
    public void setEditNavButtonMode(boolean b) {
        if (b == false) {
            accountsGUI.firstNavButton.setEnabled(true);
            accountsGUI.lastNavButton.setEnabled(true); 
            accountsGUI.nextNavButton.setEnabled(true); 
            accountsGUI.previousNavButton.setEnabled(true);
            accountsGUI.newRecordNavButton.setText("New Record");
            accountsGUI.isEditing = false;
            accountsGUI.editNavButton.setEnabled(true);
            accountsGUI.saveNavButton.setEnabled(false);
            accountsGUI.removeNavButton.setEnabled(true);
            accountsGUI.searchBarSearchButton.setEnabled(true);
        } else {
            accountsGUI.firstNavButton.setEnabled(false);
            accountsGUI.lastNavButton.setEnabled(false);
            accountsGUI.nextNavButton.setEnabled(false); 
            accountsGUI.previousNavButton.setEnabled(false);
            accountsGUI.newRecordNavButton.setText("Cancel");
            accountsGUI.isEditing = true;
            accountsGUI.editNavButton.setEnabled(false);
            accountsGUI.saveNavButton.setEnabled(true);
            accountsGUI.removeNavButton.setEnabled(false);
            accountsGUI.searchBarSearchButton.setEnabled(false);
        }
    }
    
    /**
     * Sets navigation buttons to first record mode, i.e. no first and previous
     * record pointers
     * @param b - if false then disable, enable otherwise
     */
    public void setIsFirst(boolean b) {
        if (b == true) {
            accountsGUI.firstNavButton.setEnabled(false);
            accountsGUI.previousNavButton.setEnabled(false);
        } else {
            accountsGUI.firstNavButton.setEnabled(true);
            accountsGUI.previousNavButton.setEnabled(true);
        }
    }
    
    /**
     * Sets navigation buttons to last record mode, i.e. no last and next
     * record pointers
     * @param b - if false then disable, enable otherwise
     */
    public void setIsLast(boolean b) {
        if (b == true) {
            accountsGUI.lastNavButton.setEnabled(false);
            accountsGUI.nextNavButton.setEnabled(false);
        } else {
            accountsGUI.lastNavButton.setEnabled(true);
            accountsGUI.nextNavButton.setEnabled(true);
        }
    }

    /**
     * Sets navigation buttons to no record at all mode
     * i.e. no first, previous, next or last record pointers
     * @param b - if false then disable, enable otherwise
     */
    public void setIsEmpty(boolean b) {
        if (b == true) {
            accountsGUI.removeNavButton.setEnabled(false);
            accountsGUI.firstNavButton.setEnabled(false);
            accountsGUI.previousNavButton.setEnabled(false);
            accountsGUI.lastNavButton.setEnabled(false);
            accountsGUI.nextNavButton.setEnabled(false);
            accountsGUI.editNavButton.setEnabled(false);
            accountsGUI.newRecordNavButton.setEnabled(true);
        } else {
    		
            accountsGUI.removeNavButton.setEnabled(true);
            accountsGUI.editNavButton.setEnabled(true);
    		
        }
    }
    
}
