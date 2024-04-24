using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UIInventory : MonoBehaviour
{

    private GameObject[] inventories;
    private CharacterInventory charac;
    // Start is called before the first frame update
    void Start()
    {
        inventories = GameObject.FindGameObjectsWithTag("inventory");
        charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
        organiseUI();

    }

    public void organiseUI(){
        List<PickUpObjects> temp =  charac.getObjectsCollected();
        for(int i = 0; i < temp.Count; i++){
            inventories[i].GetComponent<SpriteRenderer>().sprite = temp[i].getMySprite();
        }
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
