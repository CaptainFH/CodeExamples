using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class doorOpen : MonoBehaviour
{
    private Animator doorAnimator;
    //private SpriteRender sprite;
    private CharacterInventory charac;
    public bool opened = true;
    private Collider2D doorCollider;
     private InteractableCursor mice;
     public string neededItem;
      private Descriptions desc;
      public string nextScene;
  void Start()
    {
      charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
      doorAnimator = GetComponent<Animator>();
      doorCollider = GetComponent<BoxCollider2D>();
      mice = GameObject.FindWithTag("mouse").GetComponent<InteractableCursor>();
       desc = GameObject.FindWithTag("description").GetComponent<Descriptions>();
      //sprite = GetComponent<SpriteRender>();
      if (opened){
         doorAnimator.SetBool("doorIsOpen", true);
      }
    }
     void OnMouseOver()
     {
      if(charac.hasItem(neededItem) || opened){
        if (!opened){
        desc.display("Click to open");
        }if (opened){
          desc.display("Walk to cross");
        }
          mice.mouseAnimation.SetInteger("cursorState", 1);
          if (Input.GetMouseButton(0) && !opened){
                  doorAnimator.SetBool("doorIsOpen", true);
                  opened = true;
          }
      }else{
        desc.display("Find the key");
        mice.mouseAnimation.SetInteger("cursorState", 2);
      }
     }
     void OnMouseExit(){
      desc.display(null);
      mice.mouseAnimation.SetInteger("cursorState", 0);
     }
    // Update is called once per frame
    void Update()
    {
        if(doorCollider.IsTouching(charac.pickUpRangeC) && opened){
           SceneManager.LoadScene(nextScene);
        }
    }
}
