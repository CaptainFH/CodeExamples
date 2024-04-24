using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    public void openFirstLevel(){
        SceneManager.LoadScene("Assets/Scenes/menuScene.unity");
    }
    public void openSecondLevel(){
        SceneManager.LoadScene("Assets/Scenes/firstScene.unity");
    }
}
