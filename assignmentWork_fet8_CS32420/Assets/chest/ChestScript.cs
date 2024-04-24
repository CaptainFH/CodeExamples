using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChestScript : MonoBehaviour
{
    public static bool opened = false ;
    private Animator animation; 
    private CharacterInventory charac;
    private Collider2D openingRange;
    private InteractableCursor mice;
    private PickUpObjects key2 = new PickUpObjects();
    public GameObject key1;
    private Descriptions desc;
    private int onlyOnce = 0;
    // Start is called before the first frame update
    void Start()
    {
        charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
        animation = GetComponent<Animator>();
         openingRange = GetComponent<BoxCollider2D>();
         mice = GameObject.FindWithTag("mouse").GetComponent<InteractableCursor>();
         key2.itemName = "key2";
        desc = GameObject.FindWithTag("description").GetComponent<Descriptions>();
         openChest();
    }
     void OnMouseOver()
     {
        if (openingRange.IsTouching(charac.pickUpRangeC)){
            desc.display("Click to open");
            mice.mouseAnimation.SetInteger("cursorState", 1);
            if (Input.GetMouseButton(0) && openingRange.IsTouching(charac.pickUpRangeC)){
               //Debug.Log("called from "+ itemName);
               if (onlyOnce !=1 ){
                Instantiate(key1, transform.position, Quaternion.identity);
                onlyOnce++;
               }
                opened = !opened;
                 openChest();
            }
        }else{
            mice.mouseAnimation.SetInteger("cursorState", 2);
           if (Input.GetMouseButton(0)){
                desc.display("chest with key - get Closer to inspect");
            }
        }
     }
     void OnMouseExit(){
        mice.mouseAnimation.SetInteger("cursorState", 0);
     }

    void openChest(){
        animation.SetBool("opened", opened);
        
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
