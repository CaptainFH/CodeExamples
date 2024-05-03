using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class spikes : GroundMovement
{   
    private Animator spikeAnimation;
    public void triggered(bool trigger){
        if (spikeAnimation == null){
            spikeAnimation = GetComponent<Animator>();
        }
        if(trigger){
        groundID = groundID-2;
        spikeAnimation.SetBool("pullLever", trigger);
        }else{
            groundID = groundID+2;
            spikeAnimation.SetBool("pullLever", trigger);
        }
    }

}
