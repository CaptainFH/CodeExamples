using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Descriptions : MonoBehaviour
{
    private string textToBeDisplayed;
    private Text textDisplay;
    // Start is called before the first frame update
    void Start()
    {
        textDisplay = GetComponent<Text>();
    }
    public void display(string givenMessage){
        textDisplay.text = "";
        textDisplay.text = givenMessage;
    }
}
