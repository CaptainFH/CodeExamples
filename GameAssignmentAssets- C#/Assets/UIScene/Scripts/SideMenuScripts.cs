using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SideMenuScripts : MonoBehaviour
{
    public static bool pause = false;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    public void pauseGame(){
        if (pause){
        Time.timeScale = 1f;
        } else{
            Time.timeScale = 0f;
        }
        pause = !pause;
    }

    public void quitGame(){
        SceneManager.LoadScene("Assets/Scenes/MainMenu.unity");
    }
    public void restartGame(){
        SceneManager.LoadScene(SceneManager.GetActiveScene().path);
    }
}
