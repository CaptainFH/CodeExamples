using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class InteractableCursor : MonoBehaviour
{
    public Animator mouseAnimation;
    // Start is called before the first frame update
    void Start()
    {
        mouseAnimation = GetComponent<Animator>();
        Cursor.visible = false;
    }

    // Update is called once per frame
    void Update()
    {
       // float speed = 1 * Time.deltaTime;
        //transform.position = Vector2.MoveTowards(transform.position, (Vector2) Camera.main.ScreenToWorldPoint(Input.mousePosition), speed);
        Vector2 currentPos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
        transform.position = currentPos;
    }
}
