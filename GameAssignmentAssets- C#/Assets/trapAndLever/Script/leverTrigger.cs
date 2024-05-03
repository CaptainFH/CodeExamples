using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class leverTrigger : MonoBehaviour
{
    private playerUsingItems characShooting;
    private Animator leverAnimation;
    public bool leverActive = false;
    public GameObject[] spikesWithinLevel; 
    public bool beenShot = false;
    private Descriptions desc;
    // Start is called before the first frame update
    void Start()
    {
        characShooting = GameObject.FindWithTag("Player").GetComponent<playerUsingItems>();
        leverAnimation = GetComponent<Animator>();
        spikesWithinLevel = GameObject.FindGameObjectsWithTag("spikes");
         desc = GameObject.FindWithTag("description").GetComponent<Descriptions>();
    }
    void OnMouseOver(){
        desc.display("Lever to activate and Deactivate Spikes");
         Debug.Log(leverAnimation.GetBool("pushed"));
        if(!beenShot && characShooting.shootingStance && Input.GetMouseButton(0)){
            beenShot = true;
            leverActive = !leverActive;
            Debug.Log(leverAnimation.GetBool("pushed"));
            leverAnimation.SetBool("pushed", leverActive);
            for(int i = 0; i < spikesWithinLevel.Length; i++){
                spikesWithinLevel[i].GetComponent<spikes>().triggered(leverActive);
            }
            beenShot = false;
        }
    }
    void OnMouseExit(){
        desc.display(null);
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
