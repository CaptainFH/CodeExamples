using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class playerUsingItems : MonoBehaviour
{
    public Animator characterAnimator;
    private PlayerMovementReference isCharacMoving; 
    public bool shootingStance = false;
    private InteractableCursor mice;
    private SelectedSprites[] toBeSelected;
    // Start is called before the first frame update
    void Start()
    {
        characterAnimator = GetComponent<Animator>();
        isCharacMoving = GetComponent<PlayerMovementReference>();
        mice = GameObject.FindWithTag("mouse").GetComponent<InteractableCursor>();
        GameObject[] temp =GameObject.FindGameObjectsWithTag("inventory"); 
        toBeSelected = new SelectedSprites[temp.Length];
        for (int i = 0; i < temp.Length; i++){
        toBeSelected[i] = temp[i].GetComponent<SelectedSprites>();
        }
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKey("q") && !isCharacMoving.getIsCharacMoving()){
            shootingStance = !shootingStance;
            disableShootingStancandSprites("gun");
        
        }
        if(Input.GetKey("w") ){
            shootingStance = false;
            disableShootingStancandSprites("key1");
        }
        if(Input.GetKey("e")){
            shootingStance = false;
            disableShootingStancandSprites("key2");
        }
        if(Input.GetKey("r")){
            shootingStance = false;
            disableShootingStancandSprites("Muffin");
        }
        if (shootingStance){
            if (Input.GetMouseButton(0)){
                characterAnimator.SetTrigger("act");
            }
        }
    }

    void disableShootingStancandSprites(string name){
        if (shootingStance){
                characterAnimator.SetBool("shootingStance", true);
                mice.mouseAnimation.SetInteger("cursorState", 3);
            }else{
                characterAnimator.SetBool("shootingStance", false);
                mice.mouseAnimation.SetInteger("cursorState", 0);
            }
        for(int i = 0; i < toBeSelected.Length; i++){
            if (!(toBeSelected[i].name == name)){
                toBeSelected[i].highlight(false);
            }else{
                toBeSelected[i].highlight(true);
            }
        }
    }
}
