using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GroundMovement : MonoBehaviour
{   
    private PlayerMovementReference charac;
    private playerUsingItems isShooting;
    public int groundID;
     Vector2 targetLocation;
     bool isMoving  = false;
     float speed = 0.5f;


    // Start is called before the first frame update
    void Start()
    {
         GameObject temp = GameObject.FindWithTag("Player");
        charac = temp.GetComponent<PlayerMovementReference>();
        isShooting = temp.GetComponent<playerUsingItems>();
    }
    void OnMouseOver(){
        
        if (Input.GetMouseButton(1) && !isShooting.shootingStance){
            if( ( (!(charac.getIsCharacMoving())) )&& ((groundID -1)<= charac.getCurrentPosition() && charac.getCurrentPosition() <= (groundID +1))){
                 targetLocation = Camera.main.ScreenToWorldPoint(Input.mousePosition);
                 isMoving = true;
                 charac.setIsCharacMoving(true);
                  characterDirection();
            }
        }
    }
    // Update is called once per frame
    void Update()
    {
        //Debug.Log(charac == null);
        if (isMoving && (Vector2) charac.transform.position != targetLocation){
            float step = speed * Time.deltaTime;
            charac.transform.position = Vector2.MoveTowards(charac.transform.position, targetLocation,step);
        }
        else{
            if (charac.getIsCharacMoving() && isMoving == true){
             charac.setIsCharacMoving(false);
             charac.setCurrentPosition(groundID);
             charac.characterAnimator.SetInteger("characterChosenMove", 5);
            }
            isMoving = false;
        }
    }
    //FitargetLocation.x
    
    void characterDirection(){

        if (targetLocation.x > charac.transform.position.x && targetLocation.y >charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 9);
        }else if (targetLocation.x > charac.transform.position.x && targetLocation.y ==charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 6);
        }else if (targetLocation.x < charac.transform.position.x && targetLocation.y ==charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 4);
        }else if (targetLocation.x < charac.transform.position.x && targetLocation.y <charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 1);
        }else if (targetLocation.x == charac.transform.position.x && targetLocation.y <charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 2);
        }else if (targetLocation.x == charac.transform.position.x && targetLocation.y >charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 8);
        }else if (targetLocation.x > charac.transform.position.x && targetLocation.y <charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 3);
        }else if (targetLocation.x < charac.transform.position.x && targetLocation.y > charac.transform.position.y){
            charac.characterAnimator.SetInteger("characterChosenMove", 7);
        }
        
    }
}
