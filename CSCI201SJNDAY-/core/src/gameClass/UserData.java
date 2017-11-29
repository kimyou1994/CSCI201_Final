package gameClass;

import java.io.Serializable;
/**
 * Saves user information after user logs in.
 * Constructed by the login screen.
 * @author Joshua Luceno
 *
 */
public class UserData implements Serializable{
	private static final long serialVersionUID = -7407071641448303949L;
	private Integer currentMoney;
	private Integer armorLevel;
	private Integer weaponLevel;
	private Boolean colorOwned[];
	private Boolean isGuest;
	private Integer colorIndex;
	private String userName;
	
	public UserData(String userName, Boolean guest, int currMoney, int armorLevel, int weaponLevel, int currColor, Boolean[] ownedColors) {
		//Pull information from database and save it locally
		setIsGuest(guest);
		setCurrentMoney(currMoney);
		setArmorLevel(armorLevel);
		setWeaponLevel(weaponLevel);
		setColorIndex(currColor);
		setColorOwned(ownedColors);
		setUserName(userName);
	}

	public Integer getCurrentMoney() {
		return currentMoney;
	}


	//TODO send to server new values
	public void setCurrentMoney(Integer currentMoney) {
		this.currentMoney = currentMoney;
	}



	public Integer getArmorLevel() {
		return armorLevel;
	}


	//TODO send to server new values
	public void setArmorLevel(Integer armorLevel) {
		this.armorLevel = armorLevel;
	}



	public Integer getWeaponLevel() {
		return weaponLevel;
	}


	//TODO send to server new values
	public void setWeaponLevel(Integer weaponLevel) {
		this.weaponLevel = weaponLevel;
	}



	public Boolean[] getColorOwned() {
		return colorOwned;
	}


	//TODO send to server new values
	public void setColorOwned(Boolean colorOwned[]) {
		this.colorOwned = colorOwned;
	}

	
	public Boolean getIsGuest() {
		return isGuest;
	}


	//TODO send to server new values
	public void setIsGuest(Boolean isGuest) {
		this.isGuest = isGuest;
	}



	public Integer getColorIndex() {
		return colorIndex;
	}



	public void setColorIndex(Integer colorIndex) {
		this.colorIndex = colorIndex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
