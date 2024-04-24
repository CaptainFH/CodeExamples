using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SelectedSprites : MonoBehaviour
{
    public bool isBeenUsed = false;
    private bool obtainedB = false;
    private Image imageUsed;
    private static Animator animation;
    public string itemName;
    private CharacterInventory charac;
    // Start is called before the first frame update
    void Start()
    {  
        animation = GetComponent<Animator>();
        //imageUsed = GetComponent<Image>();
        charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
    }

    // Update is called once per frame
    void Update()
    {   
        Debug.Log(this.itemName);
        if (!obtainedB){
            if (charac.hasItem(itemName)){
                obtained();
                obtainedB = true;
            }
        }
    }
    public void highlight(bool activateAnimation){
        if (activateAnimation == isBeenUsed){
            isBeenUsed = false;
            animation.SetBool("hasItem", isBeenUsed);    
        }else{
        animation.SetBool("hasItem", activateAnimation);
        isBeenUsed = activateAnimation;
        }
    }

     public bool compare(string l){
        if (string.Compare(itemName, l) == 0){
            return true;
        }
        return false;
    }


    void obtained(){
        animation.SetBool("obtained", true);
    }
}
