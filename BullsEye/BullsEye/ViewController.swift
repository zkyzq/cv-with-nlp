//
//  ViewController.swift
//  BullsEye
//
//  Created by tangxi on 2019/2/28.
//  Copyright © 2019年 tangxi. All rights reserved.
//

import UIKit

class ViewController: UIViewController
{
    @IBOutlet weak var slider:UISlider!
    @IBOutlet weak var targetLabel:UILabel!
    @IBOutlet weak var scoreLabel:UILabel!
    @IBOutlet weak var roundLabel:UILabel!
    
    var currentValue:Int = 50
    var targetValue:Int = 0
    var score = 0
    var round = 0
    
    override func viewDidLoad()
    {
        
        super.viewDidLoad()
         // Do any additional setup after loading the view, typically from a nib.
        let thumbImageNormal = UIImage(named:"SliderThumb-Normal")!
        slider.setThumbImage(thumbImageNormal, for: .normal)
        let thumbImageHighlighted = UIImage(named:"SliderThumb-Highlighted")!
        slider.setThumbImage(thumbImageHighlighted, for: .highlighted)
        let insets = UIEdgeInsets(top:0,left:14,bottom:0,right:14)
        
        let trackLeftImage = UIImage(named:"SliderTrackLeft")!
        let trackLeftResizable = trackLeftImage.resizableImage(withCapInsets: insets)
        slider.setMinimumTrackImage(trackLeftResizable, for: .normal)
        
        let trackRightImage = UIImage(named:"SliderTrackRight")!
        let trackRightResizable = trackRightImage.resizableImage(withCapInsets: insets)
        slider.setMaximumTrackImage(trackRightResizable, for: .normal)
        startNewGame()
        
    }
    func startNewRound()
    {
        targetValue = Int.random(in: 1...100)
        currentValue = 50
        slider.value = Float(currentValue)
        round += 1
        updataLabels()
    }
    func updataLabels()
    {
        targetLabel.text = String(targetValue)
        scoreLabel.text = String(score)
        roundLabel.text = String(round)
    }
    func startNewGame()
    {
        score = 0
        round = 0
        startNewRound()
        let transition = CATransition()
        transition
    }
    @IBAction func showAlert()
    {
        let difference = abs(currentValue - targetValue)
        var points = 100 - difference
        let title: String
        if difference == 0
        {
            title = "运气逆天，赶紧去买注彩票吧,额外送您一百分！"
            points += 100
        }
        else if difference < 5
        {
            title = "太棒了，就差一点了!"
        }
        else if difference < 10
        {
            title = "很不错，继续努力!"
        }
        else
        {
            title = "差到姥姥家了= ="
        }
        score += points
        let message = "您的得分是: \(points)"
//        let message = "滑动条当前的数值是: \(currentValue)" + "\n目标数值是: \(targetValue)" + "\n两者的差是: \(difference)"
        let alert = UIAlertController(title:title,message:message,
                                      preferredStyle:.alert)
        let action = UIAlertAction(title:"OK",style:.default,handler:{ _ in self.startNewRound()})
        alert.addAction(action)
        present(alert,animated: true,completion:nil)
//        startNewRound()
    }
    @IBAction func sliderMoved(_ slider:UISlider)
    {
        //print("滑动条当前的数值是: \(slider.value)")
        currentValue = lroundf(slider.value)
    }
    @IBAction func startOver()
    {
        startNewGame()
    }
}

