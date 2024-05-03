using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PickUpObjects : MonoBehaviour
{
    public string itemName ="";

    private bool animating;
    public Vector2 targetLocation;
    private bool picked = false;
    private float speed = 2.0f;
    private bool over = false;
    private CharacterInventory charac;
    private Collider2D pickUpRange;
    private InteractableCursor mice;
    private Descriptions desc;
    private static SpriteRenderer mySpriteR;
    private static Sprite mySprite;
    // Start is called before the first frame update
    void Start()
    {
      charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
      pickUpRange = GetComponent<BoxCollider2D>();
      mice = GameObject.FindWithTag("mouse").GetComponent<InteractableCursor>();
      desc = GameObject.FindWithTag("description").GetComponent<Descriptions>();
      mySpriteR = GetComponent<SpriteRenderer>();
      mySprite = mySpriteR.sprite;
    }
     void OnMouseOver()
     {
        if (pickUpRange.IsTouching(charac.pickUpRangeC)){
            desc.display("Pick It Up- this is the  "+ itemName);
            mice.mouseAnimation.SetInteger("cursorState", 1);
            if (Input.GetMouseButton(0) && !picked && !(picked) &&pickUpRange.IsTouching(charac.pickUpRangeC)){
                animating = true;
                addToCharacter();
                picked = true;
                mySpriteR.color =  new Color(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }else{
            mice.mouseAnimation.SetInteger("cursorState", 2);
            if (Input.GetMouseButton(0)){
                desc.display("It's the " + itemName + " - Get Closer to acquire");
            }

        }
     }
     void OnMouseExit(){
        desc.display(null);
        mice.mouseAnimation.SetInteger("cursorState", 0);
     }
    void addToCharacter(){
        charac.addItem(this.GetComponent<PickUpObjects>());
    }

    public bool compare(string l){
        if (string.Compare(itemName, l) == 0){
            return true;
        }
        return false;
    }
    public Sprite getMySprite(){
        return mySpriteR.sprite;
    }

    // Update is called once per frame
    void Update()
    {
        if(animating && (Vector2) transform.position != targetLocation){
            float speedPerFrame = speed * Time.deltaTime;
            transform.position = Vector2.MoveTowards(transform.position, targetLocation, speedPerFrame);
        }
    }
}