using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CharacterInventory : MonoBehaviour
{
    public Collider2D pickUpRangeC;
    private static List<PickUpObjects> objectsCollected = new List<PickUpObjects>();
    private UIInventory myUI;
    // Start is called before the first frame update
    void Start()
    {
        pickUpRangeC = GetComponent<CircleCollider2D>();
        myUI = GameObject.FindWithTag("manager").GetComponent<UIInventory>();
    }
    void Update()
    {   
       
    }

    public void addItem(PickUpObjects item){
        objectsCollected.Add(item);
        myUI.organiseUI();

    }
    public bool hasItem(string n){
        for(int i = 0; i < objectsCollected.Count; i++){
            if(objectsCollected[i].compare(n)){
                return true;
            }
        }
        return false;
    }
    public List<PickUpObjects> getObjectsCollected(){
        return objectsCollected;
    }
}
