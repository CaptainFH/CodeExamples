using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class exitSecondLevel : MonoBehaviour
{

    private Collider2D collider;
    private CharacterInventory charac;
    // Start is called before the first frame update
    void Start()
    {   
        charac = GameObject.FindWithTag("Player").GetComponent<CharacterInventory>();
        collider = GetComponent<Collider2D>();
    }

    // Update is called once per frame
    void Update()
    {
        if(collider.IsTouching(charac.pickUpRangeC)){
           SceneManager.LoadScene("Assets/Scenes/firstScene.unity");
        }
    }
}
