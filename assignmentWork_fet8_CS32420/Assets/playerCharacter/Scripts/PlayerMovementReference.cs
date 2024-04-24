using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovementReference : MonoBehaviour
{
    private bool isCharacMoving;
    public int currentPosition;
    public Animator characterAnimator;
    // Start is called before the first frame update
    void Start()
    {
        characterAnimator = GetComponent<Animator>();
        currentPosition = 0;
        isCharacMoving = false;

    }
    public int getCurrentPosition(){
        return currentPosition;
    } 
    public void setCurrentPosition(int newID){
        currentPosition = newID;
    }
    public bool getIsCharacMoving(){
        return isCharacMoving;
    }
    public void setIsCharacMoving(bool state){
        isCharacMoving = state;
    }

}
