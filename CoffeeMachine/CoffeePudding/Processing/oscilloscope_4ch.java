import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class oscilloscope_4ch extends PApplet {

// rogerio.bego@hotmail.com
String versao="v1.3";
// 24/06/2017 => when start serial communication, processing send initial commands to arduino in eventserial
// 24/06/2017 => dynamic buffer bug fixed - qInit was not returning to 0 when q changed
// 22/05/2017 => v1.3 dynamic buffer - 1ch=400pt/ch, 2chs=200pt/ch, 3chs=130pt/ch, 4chs=100pt/ch
// 28/05/2017 => bug v1.3  => tenho 1 canal ativo, qdo ativo outro canal d\u00e1 erro na serial (disabling serialEvent())
// 14/05/2017 - BegOscopio v1.3 
// 29/01/2017 - v1.2 coloquei um valor para o trigger 0-1024 (0-5v)
//               transmitir  tv512.  (512=2.5v)
// 15/10/2015 - acrescentei mais um canal - 4canais
// 16/09/2015 - devido a falta de mem\u00f3ria no Arduino, 
//              mudei o array de int para byte,
//              ent\u00e3o, dividi os valores por 4 (1023/4=255)
// 08/09/2015 garaginoscopio v1

//constantes para a classe Dial
byte escLinear=0; // Dial com escala linear
byte escLog=1;     // Dial com escala logar\u00edtimica (base 10)
byte altMove=2; // mudar o valor ao arrastar o mouse "MouseDragged"
byte altSolta=3; // mudar o valor ao soltar o bot\u00e3o do mouse "MouseReleased"
boolean nInt=true; // n \u00e9 inteiro (arredondar), ou decimal !nInt 
boolean fmt=true; // fmt=true="formatar",  !fmt=false="n\u00e3o formatar"
boolean esperandoTrigger=false;
int vTrigger=0; // valor do trigger (subindo) 0-1024 (0-5v) 

int cor[]={color(255, 0, 0), color(0, 255, 0), color(0, 0, 255), color(255,255,0)}; // canais: red,green,blue,yellow



/*
 08-Jun-2017 - output file "dados.txt" when click button
  t (ms)<tab>ch0 (mV)<tab>ch1 (mV)
  0<tab>1215<tab>123
  1<tab>2123<tab>2
  2<tab>2350<tab>350
*/
PrintWriter output;
boolean outputOpen=false;
Botao save;
int qSave=0;


// configura\u00e7\u00e3o dos objetos
Serial port;
Com com;
Tela tela;
CanalXYZ chXYZ;
//Botao XYZ; 
//float XYZy; // coordenada y do gr\u00e1fico XYZ
//float mouseOffSet;
//boolean XYZyPegou=false; // indica se pegou o triangulo do controle do y do XYZ
//Botao selXYZ[]=new Botao[3];
//Botao medir[]=new Botao[5];
//Botao trigger[]=new Botao[5];
Botao resetEixos;
Botao resetMedir;
Canal canal[]=new Canal[4];
//byte chq=4; // qtd de canais ativos
Grupo grupo[]=new Grupo[3]; // usado para alterar v/div e ms/div simultaneamente em todos os canais usando SHIFT
// painel para os controles de Amostragem
Painel pnlAmostra; // painel
Dial dt; // delta t (tempo de cada leitura)
Dial q;  // quantidade de leituras 
Botao umaAmostra; // solicita uma Amostra
Botao variasAmostras; // solicita v\u00e1rias Amostras
Botao fluxoContinuo;  // entra com leitura a cada dt
FmtNum tTotal; // tempo total da amostragem dt*q

Botao demo[]=new Botao[3]; // bot\u00f5es para gerar sinais de demonstra\u00e7\u00e3o
float fase1, fase2, t1, t2; // fase dos DEMO //para noise(t) do demo
CheckBox verPontos; // ver os pontos da amostragem
CheckBox calcFreq; // detectar frequencia
CheckBox grafDif; // (ver) mostrar gr\u00e1fico Diferen\u00e7a (parecido com a derivada)

//Dial ruido; // usado para melhorar a detec\u00e7\u00e3o da frequencia

// painel para medir resistor/capacitor
Painel pnlRC; // o valor \u00e9 colocado em tex2 do CheckBox RC 
CheckBox RC; // ativa/desativa medidor de resistor/capacitor

// painel para o Gerador de Sinal
Painel pnlSinal;
CheckBox sinal; // f e t s\u00e3o dependentes f=1/t, t=1/f
Dial fSinal;    // f (frequencia) do Sinal (10kHz-0.125Hz) 
Dial tSinal;    // T (per\u00edodo) do Sinal (100us-8s)
Dial tonSinal;  // tempo em ON (0-100%)

// verificar se o tempo de leitura Real \u00e9 igual ao desejado
FmtNum tTotalReal, dtReal; // tempos reais de leitura enviados pelo Arduino
boolean dtErro=false;
float Q=45.0f; //tamanho do quadrado na tela

int chq=4; // qtd de canais selecionados (visiveis) 
//int qMax=102; // chq-qMax: 4-102, 3-136, 2-204, 1-408
int ch[]={0,1,2,3}; // quais canais est\u00e3o visiveis (ON)


//temporarios
int marg1, marg2; //margem temporaria para ajustar a posi\u00e7\u00e3o dos objetos

public void setup() {
   
  //size(800,700);
  frameRate(10);
  
  // inicializa\u00e7\u00e3o dos objetos 
  // d=new Dial(escLinear,altSolta,"teste","V/div",5,1,100,10,10,100,20);
  //port=new Serial(this,Serial.list()[1],9600);
  //port.bufferUntil(10); //configurado para pegar linhas inteiras
  tela=new Tela(30+10, 60, 10*Q, 12*Q);  //horizontal 10divisoes , vertical 12divisoes
  
  marg1=tela.x+tela.w+10; 
  marg2=marg1+200;
  
  //16-Jun-2017 serial port names are too long in Mac - Roman Random
  com=new Com(port, tela.x+tela.w-250, 5, 250, 55);
  //com=new Com(port, tela.x+tela.w-175, tela.y-30, 175, 20);
  
  //XYZ=new Botao("XYZ", marg2, tela.y, 45, 20);
  //XYZy=tela.y+5*Q;
  //for (int k=0; k<3;k++){
  //  selXYZ[k]=new Botao(str(k),XYZ.x+XYZ.w+6+k*(18+2),XYZ.y,18,20);
  //  selXYZ[k].cor_ativo=cor[parseInt(selXYZ[k].tex)];
  //  selXYZ[k].clicado=true;
  //}

  //  for (Grupo g:grupo){
  //     g=new Grupo(); 
  //  }
   for (byte k=0; k<3; k++){ // deve estar antes do canal
      grupo[k]=new Grupo(); 
   }

  resetEixos=new Botao("eixos",marg1+50,tela.y,45,20);
  resetMedir=new Botao("medir",resetEixos.x+resetEixos.w+2,tela.y,45,20);

  //demo & canais
  for (byte k=0; k<4; k++) {
    if (k<3) {demo[k]=new Botao(str(k+1), marg1+50+k*30, tela.y/2-10, 30, 20);}
    //medir[k]=new Botao(str(k),marg2+60+k*(18+2),tela.y+10,18,20,cor[k],cor[k]);
    //trigger[k]=new Botao(str(k),medir[k].x,medir[k].y+medir[k].h+10,medir[k].w,20,cor[k],cor[k]);
    canal[k]=new Canal(k, cor[k], marg1, tela.y+27+k*110, 143, 100); //h115
  }
  //medir[4]=new Botao("x",medir[3].x+medir[3].w,medir[3].y,medir[3].w,medir[3].h,color(200),color(0));
  //trigger[4]=new Botao("x",trigger[3].x+trigger[3].w,trigger[3].y,trigger[3].w,trigger[3].h,color(200),color(0));

  
  //chXYZ
  chXYZ=new CanalXYZ(color(255,0,255),marg1,canal[3].y+canal[3].h+10,143,80);
  chXYZ.verCanais.clicado=true;
  
  verPontos=new CheckBox("ver pontos", chXYZ.x, chXYZ.y+chXYZ.h+5, 15);
  calcFreq=new CheckBox("detectar freq.", verPontos.x, verPontos.y+verPontos.h+5, 15);
  grafDif=new CheckBox("ver",calcFreq.x+calcFreq.w,calcFreq.y,15);
  //ruido=new Dial(escLinear, altMove, !nInt, fmt, "ruido", "V", 0, 0, 2, calcFreq.x+20, calcFreq.y+17, 100, 20);

  // 08-jun-2017 - button to save data  in data.txt
  save=new Botao("salvar datax.txt",calcFreq.x,calcFreq.y+calcFreq.h+5,150,20);


  //medidor de resistor/capacitor
  pnlRC=new Painel("", tela.x, tela.y+tela.h+10, 125, 40);
  RC=new CheckBox("medir res./cap.", pnlRC.x, pnlRC.y, 15);

  //Gerador de Sinais - agora s\u00f3 gera onda quadrada, depois vai gerar triangulo, denteDeSerra, e senidal
  pnlSinal=new Painel("", pnlRC.x+pnlRC.w+10, pnlRC.y, 100, 85);
  sinal=new CheckBox("Ger.Sinal", pnlSinal.x, pnlSinal.y, 15);
  fSinal=new Dial(escLog, altSolta, !nInt, fmt, "f", "Hz", 50f, 125e-3f, 10e3f, pnlSinal.x+5, sinal.y+sinal.h+2, pnlSinal.w-10, 20);
  tSinal=new Dial(escLog, altSolta, !nInt, fmt, "T", "s", 20e-3f, 100e-6f, 8f, fSinal.x, fSinal.y+fSinal.h+2, fSinal.w, fSinal.h);
  tonSinal=new Dial(escLinear, altSolta, nInt, !fmt, "Ton", "%", 25f, 0f, 100f, tSinal.x, tSinal.y+tSinal.h+2, tSinal.w, tSinal.h);

  // posicionamento da Amostragem 
  pnlAmostra=new Painel("Amostragem", pnlSinal.x+pnlSinal.w+10, pnlSinal.y, 200, 85);
  dt=new Dial(escLog, altSolta, nInt, fmt, "dt", "s", 1e-3f, 10e-6f, 2f, pnlAmostra.x+5, pnlAmostra.y+20, 100, 20);
  dtReal=new FmtNum(0,nInt,fmt);
  q=new Dial(escLinear, altSolta, nInt, !fmt, "q", "", 100, 1, 100, dt.x+dt.w+5, dt.y, 60, 20);
  tTotal=new FmtNum(dt.v.getV()*q.v.getV(), !nInt);
  tTotalReal=new FmtNum(0,!nInt);
  umaAmostra=new Botao("uma", dt.x, dt.y+dt.h+5, 50, 20);
  variasAmostras=new Botao("varias", umaAmostra.x+umaAmostra.w+5, umaAmostra.y, umaAmostra.w, umaAmostra.h);
  fluxoContinuo=new Botao("fluxo", variasAmostras.x+variasAmostras.w+5, variasAmostras.y, variasAmostras.w, variasAmostras.h);
  


}

public void draw() {
  //==== Construir a tela e mostrar os controles =========
  background(100);
  fill(0, 255, 255); 
  textAlign(LEFT, TOP);
  textSize(18); 
  text("BegOscopio "+versao, tela.x, 12);
  fill(0); 
  textSize(12); 
  text("rogerio.bego@hotmail.com", tela.x, tela.y-15);
  tela.display();
  // Bot\u00f5es de demonstra\u00e7\u00e3o
  textSize(15);
  fill(0); 
  textAlign(RIGHT, CENTER); 
  text("DEMO", demo[0].x-5, demo[0].y+demo[0].h/2); //tela.x+tela.w+10,tela.y);
  text("RESET",resetEixos.x-5,resetEixos.y+resetEixos.h/2);
  //text("medir", medir[0].x-5,medir[0].y+medir[0].h/2);
  //text("trigger", trigger[0].x-5,trigger[0].y+trigger[0].h/2);
  chXYZ.display();
  //XYZ.display();
  //for (byte k=0; k<3;k++){
  //  selXYZ[k].display();
  //}
  for (byte k=0; k<4; k++) {
    if (k<3){demo[k].display();}
    //medir[k].display();
    //trigger[k].display();
    canal[k].display();
  }
  //medir[4].display();
  //trigger[4].display();
  verPontos.display();
  calcFreq.display();
  grafDif.display();
  //ruido.display();
  save.display();
  com.display();
  resetEixos.display();
  resetMedir.display();

  tTotal.setV(dt.v.getV()*q.v.getV());
  pnlAmostra.tex2="("+tTotal.printV()+"s)";
  pnlAmostra.display();
  dt.display();
  q.display();  
  umaAmostra.display();
  variasAmostras.display();
  fluxoContinuo.display();
  //== mostrar o dtReal, tTotalRea e o erro
  textAlign(LEFT);
  if (dtErro){ fill(255,0,0); } else {fill(0,20,0); }
  String tex="Real: dt"+dtReal.printV()+"s";
  if (fluxoContinuo.clicado==false){
     tex+="  total"+tTotalReal.printV()+"s"; 
  }
  text(tex,pnlAmostra.x+5,pnlAmostra.y+pnlAmostra.h-2);
  fill(0);
  //text("tTotal "+tTotalReal.printV(),pnlSinal.x+pnlSinal.w+10,pnlSinal.y+40);
  

  pnlRC.display();
  RC.display();

  pnlSinal.display();
  sinal.display();
  fSinal.display();
  tSinal.display();
  tonSinal.display();

  //=== se DEMO estiver ativado, ent\u00e3o, gerar os dados ===
  /*
  for (int k=0; k<3; k++) {
    if (demo[k].clicado) {
      switch(k) {
      case 0: // senoide com oscila\u00e7\u00e3o na frequencia (angulo, periodo)
        canal[0].criarDados2(noise(t), (second()/10+1)*3.0*TWO_PI);
        canal[1].criarDados2(t+TWO_PI/3, 3*TWO_PI);
        canal[2].criarDados2(t+2*TWO_PI/3, 3*TWO_PI);
        canal[3].criarDados2(t+3*TWO_PI/3,3*TWO_PI);
        t+=0.02; 
        break;
      case 1: // senoide com oscila\u00e7\u00e3o na amplitude (angulo, perido, amplitude)
        canal[0].criarDados(noise(t, t1), noise(t1, t)*5*TWO_PI, noise(t+0.1));
        canal[1].criarDados(t1, 3*TWO_PI, noise(t1));
        canal[2].criarDados(noise(t), noise(t)*3*TWO_PI, noise(t1, t));
        canal[3].criarDados(noise(t+1),noise(t)*4*TWO_PI,noise(t,t1));
        t+=0.01;
        t1+=0.05;
        break;
      case 2: // onda quadrada com ruido (angulo, periodo)
        canal[0].criarDados3(noise(t), (second()/10+1)*3.0*TWO_PI);
        canal[1].criarDados3(t+TWO_PI/3, 3*TWO_PI);
        canal[2].criarDados3(t+2*TWO_PI/3, 3*TWO_PI);
        canal[3].criarDados3(t+3*TWO_PI/3,3*TWO_PI);
        t+=0.02; 
        break;
      }
    }
  }
  */
  //=== se DEMO estiver ativado, ent\u00e3o, gerar os dados ===
  if (demo[0].clicado){
        float mf=second()/10; // de 10 em 10 segundos faz multiplos da frequencia mf*freq
        for (int k=0; k<q.v.v; k++){
           canal[0].buffer[k]=(int)(512.0f*(1+sin(2.0f*PI*fSinal.v.v*((float)k*dt.v.v)))); 
           canal[1].buffer[k]=(int)(512.0f*(1+sin(fase1+2.0f*PI*tonSinal.v.v/10.0f*fSinal.v.v*((float)k*dt.v.v))));
           canal[2].buffer[k]=(canal[0].buffer[k]+canal[1].buffer[k])/2;
           canal[3].buffer[k]=(int)(512.0f*(1+sin(2.0f*PI*fSinal.v.v*(k*dt.v.v)+sin(2*PI*mf*fSinal.v.v*(k*dt.v.v)))));
           //t+=0.002;
         }
           fase1+=2*PI/100;
        canal[0].atualizou=true;
        canal[1].atualizou=true;
        canal[2].atualizou=true;
        canal[3].atualizou=true;
  } else if (demo[1].clicado){
        //float mf=second()/10+1; // de 10 em 10 segundos faz multiplos da frequencia mf*freq
        //float qmax=1.0/(2*dt.v.v*fSinal.v.v);
        float qmax=1/(fSinal.v.v*2*dt.v.v);
        float qmin=qmax*tonSinal.v.v/100.0f;
        float qc=0.0f;
        float fator=0;
        float vq=0;
        
        float f0=(fase1/(2.0f*PI*fSinal.v.v*dt.v.v))%qmax;
        float f1=((fase1+radians(120))/(2.0f*PI*fSinal.v.v*dt.v.v))%qmax;
        float f2=((fase1+radians(240))/(2.0f*PI*fSinal.v.v*dt.v.v))%qmax;
        
        
        for (int k=0; k<q.v.v; k++){
           qc=k % qmax;
           if (qc<qmin) fator=0; else fator=1.0f;
           
           //println("k=",k," qc=",qc," qmin=",qmin," qmax=",qmax, " fase1=",(fase1/(2.0*PI*fSinal.v.v*dt.v.v))%qmax);
           //(fase1/(2PIdt))%qmax=",(fase1/(2.0*PI*dt.v.v))%qmax);

           t2=((float)k+f0) % qmax;
           if (t2<qmin) vq=0; else vq=1.0f;
           canal[0].buffer[k]=(int)(vq*512.0f*(sin(fase1+2.0f*PI*fSinal.v.v*((float)k*dt.v.v))));
           canal[3].buffer[k]=(int)(vq*1023.0f);

           t2=((float)k+f1)%qmax;
           if (t2<qmin) vq=0; else vq=1.0f;
           canal[1].buffer[k]=(int)(vq*512.0f*(sin(fase1+radians(120)+2.0f*PI*fSinal.v.v*((float)k*dt.v.v))));
           
           t2=((float)k+f2)%qmax;
           if (t2<qmin) vq=0; else vq=1.0f;
           canal[2].buffer[k]=(int)(vq*512.0f*(sin(fase1+radians(240)+2.0f*PI*fSinal.v.v*((float)k*dt.v.v)))); 
           //canal[1].buffer[k]=(int)(512.0*(1+sin(radians(120)+2.0*PI*fSinal.v.v*((float)k*dt.v.v))));
           //canal[2].buffer[k]=(int)(512.0*(1+sin(radians(240)+2.0*PI*fSinal.v.v*((float)k*dt.v.v))));
           //canal[3].buffer[k]=(int)(512.0+fator*512.0*(sin(2.0*PI*fSinal.v.v*((float)k*dt.v.v)))); 
           //canal[3].buffer[k]=(int)(512.0*(1+sin(2.0*PI*fSinal.v.v*(k*dt.v.v)+sin(2*2*PI*fSinal.v.v*(k*dt.v.v)))));
           //fase1+=TWO_PI/100.0;
           //t+=0.002;
           
         }
           //fase1+=2*PI/100;
           
        canal[0].atualizou=true;
        canal[1].atualizou=true;
        canal[2].atualizou=true;
        canal[3].atualizou=true;
      
  } else if (demo[2].clicado){
        //float mf=second()/10+1; // de 10 em 10 segundos faz multiplos da frequencia mf*freq
        float qmax=1.0f/(2*dt.v.v*fSinal.v.v);
        float qmin=qmax*tonSinal.v.v/100.0f;
        float qc=0.0f;
        float fator=0;
        for (int k=0; k<q.v.v; k++){
           qc=k % qmax;
        //   println(k," qc=",qc);
           if (qc<qmin) fator=1; else fator=0;
           canal[0].buffer[k]=(int)(fator*1023.0f); 
           if (qc<qmin) {
             canal[1].buffer[k]=(int)(512.0f*(1.0f-exp(-qc*100.0f*dt.v.v)));
           } else {
             canal[1].buffer[k]=(int)(512.0f*(exp(-(qc-qmin+1)*100.0f*dt.v.v)));
           }
           canal[2].buffer[k]=(int)(512.0f+512.0f*(sin(radians(240)+2.0f*PI*fSinal.v.v*((float)k*dt.v.v)))); 
           //canal[1].buffer[k]=(int)(512.0*(1+sin(radians(120)+2.0*PI*fSinal.v.v*((float)k*dt.v.v))));
           //canal[2].buffer[k]=(int)(512.0*(1+sin(radians(240)+2.0*PI*fSinal.v.v*((float)k*dt.v.v))));
           canal[3].buffer[k]=(int)(fator*512.0f*(sin(2.0f*PI*fSinal.v.v*((float)k*dt.v.v)))); 
           //canal[3].buffer[k]=(int)(512.0*(1+sin(2.0*PI*fSinal.v.v*(k*dt.v.v)+sin(2*2*PI*fSinal.v.v*(k*dt.v.v)))));
           
           //t+=0.002;
           
         }
           fase1+=2*PI/100;
        canal[0].atualizou=true;
        canal[1].atualizou=true;
        canal[2].atualizou=true;
        canal[3].atualizou=true;
      
  }


  //teste
    // println(grupo.length); 
    // for (int k=0;k<grupo.length;k++){
    //    println(k," ",grupo[k].conta," ",grupo[k].v); 
        
    // }
}


public void mouseClicked() {
  //-- verificar se \u00e9 para abrir Serial --
  int r=com.mouseClicado();
  if (r==1) { // retornou 1 ent\u00e3o abrir serial
    try {
      //port=new Serial(this,com.ports[com.indPort],int(com.speeds[com.indSpeed]));
      port=new Serial(this, com.ports[com.indPort], PApplet.parseInt(com.speeds[com.indSpeed]));
      port.bufferUntil(10); //configurado para pegar linhas inteiras
      com.conectado=true;
      com.erro=false;

  
    } 
    catch(Exception e) {
      println("erro abrindo Serial:", e);
      com.conectado=false;
      com.erro=true;
    }
    
    if (com.conectado){      // start default was placed when eventSerial receives >init=v1.5
      /*
      //initProgram();
      println("init delay 5000");
      delay(5000);
      println("end delay 5000");
      for (int k=0;k<4;k++){
        canal[k].chN.clicado=true;
      }
      // ligar uma amostra
     variasAmostras.clicado=true;
     if (variasAmostras.clicado) {
        port.write("vo");
      } else {
        port.write("vx");
      }
      println("Abri Serial");
      println("variasAmostra.clicado=",variasAmostras.clicado);
      */
    }
    
  } else if (r==-1) { //retornou -1 ent\u00e3o fechar serial
    port.stop();
    com.conectado=false;
    com.versionArduino.tex="";
    com.erro=false;
  }

  if (resetEixos.mouseClicado()){
    for (int k=0; k<4;k++){
     canal[k].p0=tela.y+3*Q*(k+1);//posi\u00e7\u00e3o da tens\u00e3o zero
    }
    resetEixos.clicado=false;
  }
  
  if (resetMedir.mouseClicado()){
     for (int k=0; k<4;k++){
        canal[k].telaClicou=false; 
     }
     resetMedir.clicado=false;
  }
  
 
  /*
  if (XYZ.mouseClicado()) {
    if (XYZ.clicado) {
      canal[0].chN.clicado=true;
      canal[1].chN.clicado=false;
      canal[2].chN.clicado=false;
      canal[3].chN.clicado=false;
    } else {
      canal[1].chN.clicado=true;
      canal[2].chN.clicado=true;
      canal[3].chN.clicado=true;
    }
  }
  */
  chXYZ.mouseClicado();
  //XYZ.mouseClicado();
  // canais selecionados do XYZ
  /*
  for (int k=0;k<3;k++){
     if(selXYZ[k].mouseClicado()){
        int j=10-4-(parseInt(selXYZ[0].tex)+parseInt(selXYZ[1].tex)+parseInt(selXYZ[2].tex));
        selXYZ[k].tex=str(j);
        selXYZ[k].cor_ativo=cor[j];
        selXYZ[k].clicado=true;
     } 
   }
   */
  // habilitar XYZ
  //XYZ.mouseClicado();  
  
  for (int k=0; k<4; k++) {
    if (canal[k].mouseClicado()){ // se alterou o Chn para vis\u00edvel ou n\u00e3o vis\u00edvel
       if (com.conectado){                           // enviar comando para o Arduino n\u00e3o ler esse canal
         if (canal[k].chN.clicado){
            port.write("c"+str(k)+"o");
         } else {
            port.write("c"+str(k)+"x");
         }
       }
       verificarQ();
    }
  }

  for (int k=0; k<3;k++){
    if (demo[k].mouseClicado()) { // Acionar o DEMO e desmarcas os outros 2
      if (demo[k].clicado) {
        int total=0;
        for (int k2=0; k2<3;k2++){
          if (demo[k2].clicado) total++;          
        }
        if (total<=1) {
          tonSinal.salvar();
          fSinal.salvar();
          tSinal.salvar();
        }
        for (int j=0; j<k; j++) {
          demo[j].clicado=false;
        } 
        for (int j=2; j>k; j--) {
          demo[j].clicado=false;
        }
        tonSinal.alterar=altMove;
        fSinal.alterar=altMove;
        tSinal.alterar=altMove;
      } else {
        tonSinal.restaurar(); 
        tonSinal.alterar=altSolta;
        fSinal.restaurar();
        fSinal.alterar=altSolta;
        tSinal.restaurar();
        tSinal.alterar=altSolta;
      }
    }
  }
  
 
  
  // bot\u00f5es para medir tempo x tens\u00e3o nos 4 canais (e bot\u00e3o de limpar x)
  /*
  for (int k=0;k<5;k++){
    if (medir[k].mouseClicado()){
       if (medir[k].clicado){
          for (int j=0; j<k;j++){
             medir[j].clicado=false; 
          }
          for (int j=3;j>k;j--){
            medir[j].clicado=false;
          }
       }
       if (k==4){ // limpar os retangulos
         for (int i=0; i<4; i++){
            canal[i].telaClicou=false; 
         }
       }
    }
  }
  */
  
  // bot\u00f5es para acionar o trigger nos canais
  /*
  for (int k=0;k<5;k++){
    if (trigger[k].mouseClicado()){
       if (trigger[k].clicado){
          for (int j=0; j<k;j++){
             trigger[j].clicado=false; 
          }
          for (int j=4;j>k;j--){
            trigger[j].clicado=false;
          }
          //enviar comando para o Arduino
          if (com.conectado) {
            port.write("t"+trigger[k].tex);
          }      
       } else{
         if (com.conectado){
            port.write("tx"); 
         }
       }
    }
  }
  */
  
  verPontos.mouseClicado();
  calcFreq.mouseClicado();
  grafDif.mouseClicado();

  //08-Jun-2017 write data to file
  if (save.mouseClicado()){
        // 14-Jun-2017 save fluxo or save memory
        //println("fluxoContinuo.clicado=",fluxoContinuo.clicado);
        if (fluxoContinuo.clicado){
          if (outputOpen==false){ // n\u00e3o est\u00e1 gravando, ent\u00e3o iniciar a grava\u00e7\u00e3o
            //println("outputOpen==false => ",outputOpen);
            String fileName ="dataf"+nf(year(),4)+nf(month(),2)+nf(day(),2)+nf(hour(),2)+nf(minute(),2)+nf(second(),2)+".txt";
            output=createWriter(fileName);
            outputOpen=true;
            save.tex="salvando";
            // cabe\u00e7alho
            //output.println("BegOscopio v"+versao+" "+nf(year())+"-"+nf(month())+"-"+nf(day())+" "+nf(hour())+":"+nf(minute())+":"+nf(second()));
            output.print("dt(");output.print(dt.v.printV());output.print(dt.unidade);output.print(")");
            for (int k=0; k<4; k++){
               if (canal[k].chN.clicado){
                 output.print('\t');output.print("ch");output.print(k);output.print("(mV)");
               }
            }
            output.println();
            qSave=0;
            // ao entrar cada dado no fluxo gravar em output.print()
            // gravar na rotina de entrada
          } else { // save j\u00e1 est\u00e1 gravando, ent\u00e3o parar a grava\u00e7\u00e3o
            //println("outputOpen==true => ",outputOpen);
            output.close();
            outputOpen=false;
            qSave=1;
            if (qSave>10) {qSave=1;}
            save.tex="salvar datax.txt" + "-"+qSave;
            save.clicado=false;
          }
        } else {
          String fileName ="data"+nf(year(),4)+nf(month(),2)+nf(day(),2)+nf(hour(),2)+nf(minute(),2)+nf(second(),2)+".txt";
          output=createWriter(fileName);
          // cabe\u00e7alho
          //output.println("BegOscopio v"+versao+" "+nf(year())+"-"+nf(month())+"-"+nf(day())+" "+nf(hour())+":"+nf(minute())+":"+nf(second()));
          output.print("dt(");output.print(dt.v.printV());output.print(dt.unidade);output.print(")");
          for (int k=0; k<4; k++){
             if (canal[k].chN.clicado){
               output.print('\t');output.print("ch");output.print(k);output.print("(mV)");
             }
          }
          output.println();
          // dados
          float f=5000.0f/1023.0f;
          for (int k2=0; k2<q.v.v;k2++){
            output.print(k2);
            for (int k=0; k<4; k++) {
              if (canal[k].chN.clicado){
                output.print('\t');output.print(PApplet.parseInt(canal[k].v[k2]*f));
              }
            }
            output.println();
          }
          
          output.close();
          qSave+=1;
          if (qSave>10) {qSave=1;}
          save.tex="salvar datax.txt" + "-"+qSave;
          save.clicado=false;
        }
  }
  //ruido.mouseClicado();

  //se clicou em dt ou q ent\u00e3o enviar comando para Arduino e ajustar tela
  if (dt.mouseClicado()) { // se true alterou dt, ent\u00e3o ajustarFt() (escala de t na tela)
    enviarDt();
    ajustarFt();
  }
  if (q.mouseClicado()) { // se true alterou q, ent\u00e3o ajustarFt()
    enviarQ();
    ajustarFt();
  }

  if (RC.mouseClicado()) {
    if (com.conectado) {
      if (RC.clicado) {
        port.write("ro");
      } else {
        port.write("rx");
        RC.tex2="";
      }
    } else {
      RC.clicado=false;
    }
  }

  if (umaAmostra.mouseClicado()) { // receber apenas Uma Amostra
    variasAmostras.clicado=false;
    fluxoContinuo.clicado=false;
    if (outputOpen) {
      fecharDados();
    }
    if (com.conectado) {
      port.write("1"); 
    }
    umaAmostra.clicado=false;
    // verificar se tem algum trigger acionado para que ele fique esperando o disparo
    // vai ficar piscando para indicar que est\u00e1 aguardando o disparo.
    int k2=-1;
    for (int k=0; k<4;k++){
      if (canal[k].trigger.clicado) {
         k2=k;
         break; 
      }
    }
    //println("k2=",k2);
    
    if (k2>=0 && k2<=3){
       pnlAmostra.piscar=true;
       canal[k2].trigger.piscar=true;
       esperandoTrigger=true;
    } else {
       pnlAmostra.piscar=false;
       esperandoTrigger=false;
     }
  }
  if (variasAmostras.mouseClicado()) {
    umaAmostra.clicado=false;
    fluxoContinuo.clicado=false;
    if (outputOpen) {
      fecharDados();
    }
    if (com.conectado) {
      if (variasAmostras.clicado) {
        port.write("vo");
      } else {
        port.write("vx");
      }
    } else {
      variasAmostras.clicado=false;
    }
  }
  if (fluxoContinuo.mouseClicado()) {
    umaAmostra.clicado=false;
    variasAmostras.clicado=false;
    if (com.conectado) {
      if (fluxoContinuo.clicado) {
        port.write("fo");
        
      } else {
        port.write("fx");
        if (outputOpen){
          fecharDados();
        }
      }
    } else {
      fluxoContinuo.clicado=false;
    }
  }

  if (sinal.mouseClicado()){
     if (com.conectado){
        if (sinal.clicado){
           port.write("so"); 
        } else {
           port.write("sx");
        }
     }
  }
  
  if (fSinal.mouseClicado()){
    tSinal.setV(1/fSinal.v.v);
    enviarCmd("tSinal");
  }
  if (tSinal.mouseClicado()){
     fSinal.setV(1/tSinal.v.v);
     enviarCmd("tSinal");
  }
  if (tonSinal.mouseClicado()){
    enviarCmd("tonSinal");
  }
}

public void verificarQ(){
  chq=0; // contar qtd de canais ativos
  for (int k=0; k<4; k++){
     if (canal[k].chN.clicado){
       chq+=1;
     }
  }
  //q.vMax=408.0/chq;
  switch (chq){
     case 0:
       q.vMax=0;
       break;
     case 1:
       q.vMax=400;
       break;
     case 2:
       q.vMax=200;
       break;
     case 3:
       q.vMax=130;
       break;
     case 4:
       q.vMax=100;
       break;
  }
  if (q.v.v>q.vMax) {
    q.setV(q.vMax);
    ajustarFt();
  } else {
    q.setV(q.v.v);
  }
}

public void mousePressed() {
  //d.mousePressionou(); 
  for (int k=0; k<4; k++) {
    canal[k].mousePressionou();
  }
  chXYZ.mousePressionou();
  dt.mousePressionou();
  q.mousePressionou();
  //ruido.mousePressionou();

  // s\u00f3 para aparecer o verde do pressionado
  umaAmostra.mousePressionou();
  variasAmostras.mousePressionou();
  fluxoContinuo.mousePressionou();

  fSinal.mousePressionou();
  tSinal.mousePressionou();
  tonSinal.mousePressionou();
  
  for (int k=0; k<3;k++){
     demo[k].mousePressionou(); 
  }
  resetEixos.mousePressionou();
  resetMedir.mousePressionou();

}

public void mouseReleased() {
  // d.mouseSoltou();
  for (int k=0; k<4; k++) {
    canal[k].mouseSoltou();
  }
  chXYZ.mouseSoltou();

  for (int k=0; k<3;k++){
     demo[k].mouseSoltou(); 
  }
  resetEixos.mouseSoltou();
  resetMedir.mouseSoltou();
  // s\u00f3 para aparecer o verde do pressionado
  umaAmostra.mouseSoltou();
  variasAmostras.mouseSoltou();
  fluxoContinuo.mouseSoltou();


  //se soltar o mouse no dt ou q, ent\u00e3o enviar os dados para o Arduino
  if (dt.mouseSoltou()) {
    enviarDt();
    ajustarFt();
  }
  if (q.mouseSoltou()) {
    enviarQ(); 
    // acertar as escalas ft de cada canal
    ajustarFt();
  }


  //ruido.mouseSoltou();

  if (fSinal.mouseSoltou()) {
    tSinal.setV(1/fSinal.v.v);
    enviarCmd("tSinal");
  }
  if (tSinal.mouseSoltou()) {
    fSinal.setV(1/tSinal.v.v);
    enviarCmd("tSinal");
  }
  if (tonSinal.mouseSoltou()){
    enviarCmd("tonSinal");
  }
  
  
  // controle do y do XYZ
  //if (XYZ.clicado){
  //    XYZyPegou=false; 
  //}


}

public void mouseMoved() {
  //teste
  //  canal[0].cor=get(mouseX,mouseY);
  //    println("cor=",canal[0].cor);
  com.mouseMoveu();
  
  for (int k=0; k<4; k++) {
    canal[k].mouseMoveu();
  } 
  chXYZ.mouseMoveu();
  dt.mouseMoveu();
  q.mouseMoveu();
  //ruido.mouseMoveu();

  fSinal.mouseMoveu();
  tSinal.mouseMoveu();
  
  tonSinal.mouseMoveu();
}

public void mouseDragged() {
  //d.mouseArrastou(); 
  for (int k=0; k<4; k++) {
    canal[k].mouseArrastou();
  }
  chXYZ.mouseArrastou();
  dt.mouseArrastou();
  q.mouseArrastou();
  //ruido.mouseArrastou();

  if (fSinal.alterar==altSolta){
    fSinal.mouseArrastou();
    tSinal.mouseArrastou();
  } else {
    if (fSinal.mouseArrastou()){
       tSinal.setV(1/fSinal.v.v); 
    }
    if (tSinal.mouseArrastou()){
      fSinal.setV(1/tSinal.v.v);
    }
  }
  
  
  tonSinal.mouseArrastou();

  // controle do y do XYZ
  //if (XYZ.clicado){
  //   if (XYZyPegou){
  //    XYZy=constrain(mouseY,tela.y,tela.y+10*Q)-mouseOffSet; 
  //   }
  //}
}


public void keyReleased(){
  keyPressed=false;
}


public void fecharDados(){
      output.close();  
      outputOpen=false;
      if (qSave>10) {qSave=1;}
      save.tex="salvar datax.txt" + "-"+qSave;
      save.clicado=false;
}


/* ==========================================
     Comando enviados para o Arduino
   ========================================== */

//=== Ger.Sinal - Se alterou f/T/Ton - enviar comando para Arduino ==
public void enviarCmd(String cmd){
  if (cmd.equals("tSinal")){
    if (com.conectado){
         port.write("p"+tSinal.v.printV());
         println("p"+tSinal.v.printV());
     }
  } else if (cmd.equals("tonSinal")){
    if (com.conectado){
         port.write("o"+tonSinal.v.printV()+"%");
         println("o"+tonSinal.v.printV()+"%");
      }
  }
}

//==Se alterou dt ou q enviar comando para Arduino e ajustar a escala da tela ==
public void enviarDt() {
  if (com.conectado) {
    port.write("d"+dt.v.printV());
  } 
  // acertar as escalas ft de cada canal
  ajustarFt();
}
public void enviarQ() {
  if (com.conectado) {
    port.write("q"+q.v.printV()+".");
  }
}

public void ajustarFt() {
  float ftNew=dt.v.getV()*q.v.getV()/10.0f;
  println("ftNew=",ftNew," dt=",dt.v.getV()," q=",q.v.getV());
  for (int k=0; k<4; k++) {
    canal[k].ft.setV(ftNew);
  }
}

/*=====================================
      Entrada do Evento Porta Serial 
  =====================================*/
public void serialEvent(Serial p) {
  //if (p.available()>0) {}
 try { 
  String cmd="", val="";
  String tex=p.readStringUntil(10);
  //print(">>>> ",tex); //eliminar
  if (tex.charAt(0)=='>') { //comando: >cmd=v1(tab)v2(tab)v3(tab)
    int i=tex.indexOf("=");
    if (i>=0) { // encontrou sinal "=" (igual)  obs: i=-1 => n\u00e3o encontrou o sinal '='
      cmd=tex.substring(1, i); // pegar o comando obs: substring(inclusive,exclusive)
      val=tex.substring(i+1); // pegar o valor
      //println("cmd=",cmd," val=",val);
      if (cmd.equals("init")) { // init
        println("versionArduino=<",val,">");
        com.versionArduino.tex=".ino "+val.substring(0,val.length()-1);
        //start all channels
        for (int k=0;k<4;k++){
          canal[k].chN.clicado=true;
        }
       // enviar dt
        enviarDt();
       // verificar e enviar q
       verificarQ();
        enviarQ();
        
        // send command to Arduino -> start Signal Gennerator 60Hz tOn25%
        enviarCmd("tSinal");
        enviarCmd("tonSinal");
        sinal.clicado=true;
        port.write("so");
        
        // ligar varias amostra
       variasAmostras.clicado=true;
       port.write("vo");
       
       //if (variasAmostras.clicado) {
       //   port.write("vo");
       // } else {
       //   port.write("vx");
       // }
        println("Abri Serial");
        println("variasAmostra.clicado=",variasAmostras.clicado);
        
      } else if (cmd.equals("f")) { // entra fluxo de dados - deslocar dados e armazenar no final
        String tex2[]=splitTokens(val); //val = "0(t)dtReal(t)ch0(t)ch1(t)ch2"
        //deslocar os dados para baixo, para incluir o novo dado no final
        for (int j=0; j<4; j++) {
          for (int k=1; k<q.v.v; k++) {
            canal[j].v[k-1]=canal[j].v[k];
          }
        }
        canal[0].v[PApplet.parseInt(q.v.v-1)]=PApplet.parseInt(tex2[2]);
        canal[1].v[PApplet.parseInt(q.v.v-1)]=PApplet.parseInt(tex2[3]);
        canal[2].v[PApplet.parseInt(q.v.v-1)]=PApplet.parseInt(tex2[4]);
        canal[3].v[PApplet.parseInt(q.v.v-1)]=PApplet.parseInt(tex2[5]);
        dtReal.setV(PApplet.parseFloat(tex2[1]));
        if (dtReal.v-dt.v.v>1.1f*dt.v.v){ dtErro=true;} else {dtErro=false;}
       
        // salvar em arquivo
        if (outputOpen) {
          float f=5000.0f/1023.0f;
          //for (int k2=0; k2<q.v.v;k2++){
            int k2=PApplet.parseInt(q.v.v-1);
            output.print(qSave);
            qSave+=1;
            for (int k=0; k<4; k++) {
              if (canal[k].chN.clicado){
                output.print('\t');output.print(PApplet.parseInt(canal[k].v[k2]*f));
              }
            }
            output.println();
            if (qSave % 100==0) { // de 100 em 100
              save.tex="salvando "+nf(qSave);
              output.flush();
            }
          //}
        }

        //println("cmd=",cmd," val=",val," dtReal=",dtReal.printV());
       
       
      } else if (cmd.equals("chq")) {       // entra qtd e quais canais ser\u00e3o recebidos
        int v[]=PApplet.parseInt(splitTokens(val));
 //voltar        //        println("========================");
 //voltar        //        println("cmd=",cmd," val=",val);
        chq=v[0];
        for (int k=0;k<chq;k++){
          ch[k]=v[k+1];
        }
 //voltar        //        println("chs=",chq);
        for (int k=0;k<chq;k++){
 //voltar        //           println("ch[",k,"]=",ch[k]); 
        }
 //voltar        //        println("========================");
      } else if (cmd.equals("v")) { // entrada de Varias Amostra
        int v[]=PApplet.parseInt(splitTokens(val));
 //voltar        //        println("tex=",tex);
        //println("cmd=",cmd," val=",val);
        //println("v.length=",v.length," chq=",chq);
        //for (int k=0; k<v.length; k++){
        //   print(" v[",k,"]=",v[k]); 
        //}
        //println("");
        
        if (v.length==chq+1){ // >v=1 1024 100 300 300
          for (int k=0; k<chq; k++){
             canal[ch[k]].buffer[v[0]]=v[k+1]; 
          }
        }
 /*   
        int kk=v[0]; // indice da matriz
        canal[0].buffer[v[0]]=v[1];
        canal[1].buffer[v[0]]=v[2];
        canal[2].buffer[v[0]]=v[3];
        canal[3].buffer[v[0]]=v[4];
*/
    } else if (cmd.equals("q")) { // quantidade de variaveis
       // q.setV(float(val));
      } else if (cmd.equals("dt")) { // tamanho do dt (ms)
        //dt.val=float(val);
      } else if (cmd.equals("tTotalReal")) { // tempo total da amostra
        //println("atualizou");
        tTotalReal.setV(PApplet.parseFloat(val));
        //text(tTotalReal,pnlAmostra.x+2,pnlAmostra.y+pnlAmostra.h);
 //voltar        //        println("cmd=",cmd," val=",val," tTotalReal=",tTotalReal.printV());
        canal[0].atualizou=true;  // terminou de entrar os dados ent\u00e3o
        canal[1].atualizou=true;  //  carregar do buffer
        canal[2].atualizou=true;
        canal[3].atualizou=true;
        if (esperandoTrigger){
           esperandoTrigger=false;
           pnlAmostra.piscar=false;
           for (int k=0; k<4;k++){
             canal[k].trigger.piscar=false;
           }
           
        }
      } else if (cmd.equals("dtReal")){
        dtReal.setV(PApplet.parseFloat(val));
        if (dtReal.n>dt.v.n+10){ dtErro=true;} else {dtErro=false;}
        //text(dtReal,pnlAmostra.x+2,pnlAmostra.y+pnlAmostra.h-12);
 //voltar        //        println("cmd=",cmd," val=",val," dtReal=",dtReal.printV());
        
      } else if (cmd.equals("r") || cmd.equals("c") || cmd.equals("rc")) { // valor do resistor
        String tex2[]=splitTokens(val, "\t\r");
        //i=val.indexOf("\t");
        //for (int k=0; k<tex2.length; k++){
        //   print("tex2[",k,"]=",tex2[k]); 
        //}
        //println();
        if (cmd.equals("rc")) cmd="";
        RC.tex2=cmd.toUpperCase()+" "+tex2[1]+" ("+tex2[0]+")";
        
      } else if (cmd.charAt(0)=='?') {  // carregando as configura\u00e7\u00f5es do Arduino (ao conectar) 
        cmd=cmd.substring(2); // eliminar 2 caracteres iniciais "? comando"
        val=val.substring(0,val.length()-2); // eliminar 2 caracteres finais:  \n\r(13,10)(^M^J) (retorno de linha)        
 //voltar        //        println("cmd=",cmd," val=",val);
        if (cmd.equals("q")){ // val=100
          q.v.v=PApplet.parseFloat(val);
        } else if (cmd.equals("dt")){
          char unid=val.charAt(val.length()-2);
          val=val.substring(0,val.length()-2);
 //voltar        //          println("unid=",unid," val=",val);
          if (unid=='u'){
            val=val+"e-6";            
          }else{
            val=val+"e-3";
          }
 //voltar        //          println("val=",val);
          dt.setV(PApplet.parseFloat(val));
          ajustarFt();
          
        }else if (cmd.equals("canalTrigger")){ // val= 0,1,2,x
           for (int k=0;k<4;k++){canal[k].trigger.clicado=false;}
           if (!val.equals("x")){
              canal[PApplet.parseInt(val)].trigger.clicado=true;   
           }
        } else if (cmd.equals("uma")){ // val= 0 ou 1
          //umaAmostra.clicado=boolean(int(val));
        }else if (cmd.equals("varias")){ // val= 0 ou 1
          //variasAmostras.clicado=boolean(int(val));
        }else if (cmd.equals("fluxo")){ // val= 0 ou 1
          fluxoContinuo.clicado=PApplet.parseBoolean(PApplet.parseInt(val));
        }else if (cmd.equals("lerRC")){ // val= 0 ou 1
          RC.clicado=PApplet.parseBoolean(PApplet.parseInt(val));
        }else if (cmd.equals("pwmOn")){ // val=0 ou 1 (false/true) 
          sinal.clicado=PApplet.parseBoolean(PApplet.parseInt(val));
          //println("sinal.clicado=",sinal.clicado," val=",val," boolean(val)=",boolean(val));
          //for (int k=0; k<val.length();k++){
          //   println(k," - ",val.charAt(k)," - ",byte(val.charAt(k)));
          //}
          //println("int(val)=",int(val)," int(''0'')",int("0")," int(''1'')",int(1));
          //println("b(''0'')=",boolean("0")," b(''1'')=",boolean("1")," b('0')=",boolean('0')," b('1')=",boolean('1'));
        }else if (cmd.equals("pwmP")){ // cmd="pwmP", val=" 100000us"
          val=val.substring(0,val.length()-2)+"e-6"; // remover "us" e colocar "e-6" (microsegundos)
          tSinal.setV(PApplet.parseFloat(val));
          fSinal.setV(1/tSinal.v.v);
          //println("pwmP=",float(val));
        }else if (cmd.equals("pwmPon")){  // cmd="pwmPon", val="25%"
          val=val.substring(0,val.length()-1);
          tonSinal.setV(PApplet.parseFloat(val));
 //voltar        //          println("pwmPon=",float(val));
        }else {
           print("else >>>> ",tex);
         }
      }
    }
    //println("cmd=",cmd);
    //println("val=",val);
  }
 }
 catch(RuntimeException e){
    e.printStackTrace();
    
 }
}
class Botao{
   int x,y,w,h;
   String tex;
   boolean mPressionou=false;
   boolean clicado=false;
   int cor_ativo=color(0,255,0);
   int cor_fio=color(0);
   
   
   //constructor
   Botao(String tex_,int x_,int y_, int w_, int h_){
      tex=tex_; x=x_; y=y_; w=w_; h=h_;
   } 
   Botao(String tex_,int x_,int y_, int w_, int h_,int cor_ativo_, int cor_fio_){
      tex=tex_; x=x_; y=y_; w=w_; h=h_;
      cor_ativo=cor_ativo_;
      cor_fio=cor_fio_;
   }
   public void display(){
     if (mPressionou){
        fill(0,100,0);
     } else if (clicado){
         fill(cor_ativo); //fill(0,255,0); 
      } else{
        fill(200);
      }
      stroke(cor_fio); strokeWeight(1);
      rect(x,y,w,h); 
      textAlign(CENTER,CENTER);
      if (cor_ativo==color(0,0,255) && clicado){ // usar branco para texto na cor de fundo blue
        fill(255);
      } else{
        fill(0);
      } textSize(14); text(tex,x+w/2,y+h/2);
     // if (tex.equals("1")) println("mPressionou=",mPressionou);
   }
   
   public void mousePressionou(){
     if (mouseButton==LEFT) {
       if (mouseX>x && mouseX<x+w && mouseY>y && mouseY<y+h){
          mPressionou=true;
       }
     }
   }
   public void mouseSoltou(){
      if (mPressionou) mPressionou=false;
   }
   
   public boolean mouseClicado(){ // retorna se foi clicado ou n\u00e3o
     boolean ret=false;
     if (mouseButton==LEFT){
       if (mouseX>x && mouseX<x+w && mouseY>y && mouseY<y+h){
          clicado=!clicado;
          //println("clicado=",clicado);
          ret=true;
          mPressionou=false;
       }
     }
     return ret;
   }
   
   
 /*  void mouseSoltou(){
       if (!fixar) {
          clicado=false; 
        }
   }
   */
}
class Canal{
 byte n;
 Botao chN;
 int nCor;
 int x,y,w,h;
 Dial fm;  // fator de escala para a voltagem (vertical)
 Dial ft; // fator de escala para o tempo (horizontal)
 //CheckBox inv; // inverter o sinal
 CheckBox trigger; // trigger
 CheckBox curva; // suavizar as curvas com curveVertex() / vertex()
 CheckBox medir; // medir tempo e tens\u00e3o
 // armazenamento dos dados recebidos do Garagino
 int qMax=408;
 int v[]=new int[qMax];
 int buffer[]=new int[qMax];
 boolean atualizou=false;
 //int picos[]=new int[qMax];
 //int qPicos;
 //int vales[]=new int[qMax];
 //int qVales;
 int dif[]=new int[qMax]; // valores diferenciais v[t+1]-v[t]
 int picos[]=new int[qMax];
 int qPicos;
 float p0;   // posi\u00e7\u00e3o tens\u00e3o zero (0)
 boolean pegouP0=false; // indica que mouse pegou o p0
 float p0Trigger; // posi\u00e7\u00e3o do trigger
 boolean pegouTrigger=false; // indica que mouse pegou o trigger
 float dP0Trigger; // diferen\u00e7a entre p0 e p0trigger
 float p0Out; // posi\u00e7\u00e3o pixel do triangulo qInit
 boolean pegouOut=false; // indica que pedou o triangulo do Out
 float mouseOffSet; // para deslocamento dos objetos (ex: p0, p0Trigger, p0Out)
 //float fCalc, tCalc; // frequencia e periodo calculados a partir dos picos 
 FmtNum fCalc=new FmtNum(0,!nInt,fmt);
 FmtNum tCalc=new FmtNum(0,!nInt,fmt); // frequencia e periodo calculados a partir dos picos 
 float fa=5.0f/(1023.0f); // (dividi por 4 pois tive falta de memoria no garagino) 16/09/2015 
                       // fator garagino (entrada analogica = 10bits) fa=5/1023unidades
 //retangulo de medi\u00e7\u00e3o da Tela
 boolean telaClicou=false;
 float xi,yi,dx,dy; // retangulo de medir tempo e tens\u00e3o na tela
 
 // 11/May/2017 triangle to move points out of visible window (qOut) for k=qInit; k<q; k++
 //  qOut=qTotal-qVisible => qVisible=10*df/dt, qTotal (q.v.v)
 int qOut=0;
 int qInit=0; // qInit=int((px-leftWindow) * qOut/(10Q)) , Q=size of square in pixel
 
 //constructor
 Canal(byte n_,int nCor_, int x_, int y_, int w_, int h_){
     n=n_; nCor=nCor_;
     x=x_; y=y_; w=w_; h=h_;
     //chN=new Botao("Ch-"+str(n),x,y,w/2,20,nCor,nCor);
     chN=new Botao("Ch-"+str(n),x,y,w/2,15,nCor,nCor);
     //inv=new CheckBox("INV",x+w/2+8,y+4,12);
     trigger=new CheckBox("Trigger",x+w/2+3,y+3,14);
     chN.clicado=true;
     fm=new Dial(escLog,altMove,!nInt,fmt,"","v/div",2f,10e-3f,20f,x+10,y+21,w-20,20,1);
     ft=new Dial(escLog,altMove,!nInt,fmt,"","s/div",10e-3f,20e-6f,20f,x+10,fm.y+fm.h+3,w-20,20,2);
     p0=tela.y+3*Q*(n+1);//posi\u00e7\u00e3o da tens\u00e3o zero
     p0Trigger=p0;
     medir=new CheckBox("medir",ft.x,ft.y+ft.h+5,15);
     curva=new CheckBox("curva",ft.x+ft.w/2,ft.y+ft.h+5,15);
 }
  
  public void display(){
    //vTrigger=vTrigger1;
    //println("n=",n);
     // verificar se tem dados atualizados no buffer
     
     if (atualizou){
       arrayCopy(buffer,v);
       atualizou=false;
     }
     
     // grupoUpdate
     fm.grupoUpdate();
     ft.grupoUpdate();
     
     //== mostrar os controles ==
     strokeWeight(2); stroke(nCor); noFill();
     rect(x,y,w,h);
     chN.display();
     if (chN.clicado && (!chXYZ.XYZ.clicado || chXYZ.verCanais.clicado)){
       //inv.display();
       trigger.display();
       fm.display();
       ft.display();
       medir.display();
       curva.display();
       
      
      //if (XYZ.clicado){
        
      //  displayXYZ();       //mostrar XY 
      //} else{
        
       //== mostrar a linha P0 de tens\u00e3o zero
       strokeWeight(1); stroke(nCor,150);
       line(tela.x-10*n,p0,tela.x+tela.w,p0);
       fill(nCor); noStroke();
       triangle(tela.x-10*n,p0,tela.x-10-10*n,p0-10,tela.x-10-10*n,p0+10);
      
      //== mostrar a linha do trigger se o trigger estiver acionado
      if (trigger.clicado){
        if (!pegouTrigger) {
          p0Trigger=fy(vTrigger); //-fy(vTrigger);
        }
        //println(vTrigger," ",fy(vTrigger));
        strokeWeight(2); stroke(nCor,100);
        line(tela.x-10*n,p0Trigger,tela.x+tela.w,p0Trigger);
        fill(nCor); noStroke();
        triangle(tela.x+tela.w,p0Trigger,tela.x+tela.w+10,p0Trigger,tela.x+tela.w+10,p0Trigger-20);
      }

      //== mostrar o triangulo do OUT (pontos que est\u00e3o fora da janela)
      qOut=PApplet.parseInt(q.v.v-10.0f*ft.v.v/dt.v.v);
      //println("qOut=",qOut," qInit=",qInit," q.v.v=",q.v.v," ft.v.v=",ft.v.v," dt.v.v=",dt.v.v);
      if (qOut>0){
        //float fq=10*Q/qOut;
        fill(nCor); noStroke();
        //float p0Out=tela.x+tela.w-qInit*fq;
        triangle(p0Out,p0,p0Out-10,p0+10,p0Out+10,p0+10);
      } else {
        qInit=0;
        p0Out=tela.x+tela.w;
      }
      
      
      //if (calcFreq.clicado) analisarCurva(); else {qPicos=0; qVales=0;}
        //tirei o analisarCurva() em 19/09/15 para por o curvaDiferencial
        displayXt();      // mostrar Xt
        
      //}
      displayRect();
     }
     
     
  }
  
  
  //=== mouse pega p0 e move ===
  public void p0Pressionou(){
    int pini;
    int pfim;
    if (chN.clicado){
      pini=tela.x-10-10*n;
      pfim=tela.x-10*n;
      if (mouseX>pini && mouseX<pfim && mouseY>(p0-10) && mouseY<(p0+10)){ 
        // clicou no triangulo da origem (esquerdo)
        mouseOffSet=mouseY-p0;
        dP0Trigger=p0Trigger-p0;
        pegouP0=true;
       }
     }   
   }

  
  public void p0Arrastou(){
   if (pegouP0){
      p0=constrain(mouseY,tela.y,tela.y+tela.h)-mouseOffSet; 
      p0Trigger=p0+dP0Trigger;
      if (keyPressed && key==CODED && keyCode==SHIFT){
        int k2=2;
        if (p0<=tela.y+tela.h/2){
            for (int k=0;k<4;k++){
               if (k != n){
                 canal[k].p0=constrain(tela.y+(p0-tela.y)*k2,tela.y,tela.y+tela.h);
                 k2++;
               }
            }
        } else {
            for (int k=0;k<4;k++){
              if (k!=n){
                canal[k].p0=constrain(tela.y+tela.h-(tela.y+tela.h-p0)*k2,tela.y,tela.y+tela.h);
                k2++;
              }
            }
        }
      }
   }
  }

 //=== mouse pega triangulo p0Trigger ===
 public void  p0TriggerPressionou(){
    int pini;
    int pfim;
    if (chN.clicado){
        // clicou no triangulo do trigger (direita)
        pini=tela.x+tela.w;
        pfim=tela.x+tela.w+10;
        if (mouseX>pini && mouseX<pfim && mouseY>(p0Trigger-20) && mouseY<(p0Trigger)){ 
           mouseOffSet=mouseY-p0Trigger;
           pegouTrigger=true;
        }
    }
 }

 public void  p0TriggerArrastou(){
  if (pegouTrigger){
      p0Trigger=constrain(mouseY,fy(1024),p0)-mouseOffSet; 
      println("pegouTrigger=true  p0Trigger="+str(p0Trigger));
   }
 }

 //=== mouse pega triangulo p0Out ===
 public void p0OutPressionou(){
   if (chN.clicado){
      // clicou no triangulo do deslocamento da janela (pontos escondidos)
      if (mouseX>p0Out-10 && mouseX<p0Out+10 && mouseY>p0 && mouseY<(p0+10)){
        println("n=",n);
        mouseOffSet=mouseX-p0Out;
        pegouOut=true;
      }
   }
 }

 public void p0OutArrastou(){
  if (pegouOut){
      p0Out=constrain(mouseX,tela.x,tela.x+tela.w);
      float fq=10*Q/qOut;
      //float p0Out=tela.x+tela.w-qInit*fq;
      qInit=PApplet.parseInt((tela.x+tela.w-p0Out)/fq);
      //println("pegouOut=true p0Out="+str(p0Out)," qInit=",qInit);
      if (keyPressed && key==CODED && keyCode==SHIFT){
            for (int k=0;k<4;k++){
               if (k != n){
                 //canal[k].ft.v.v=canal[n].ft.v.v;
                 canal[k].ft.setV(ft.v.v);
                 canal[k].qInit=qInit;
                 // recalcular o p0Out
                 canal[k].p0Out=p0Out;
               }
            }
      }      
   }
 }

  
  public void displayXt(){ // modo em fun\u00e7\u00e3o do tempo
    float px, py;
    int pt0,pt1;
    stroke(nCor);strokeWeight(2); noFill();
    beginShape();
      for (int k=qInit; k<q.v.v; k++){
        px=fx(k-qInit);
        if (px>tela.x+tela.w || px<tela.x) {
           break; 
        }
        py=fy(v[k]);
        if (curva.clicado) {
          curveVertex(px,py);
        } else {
          vertex(px,py);
        }
        if (verPontos.clicado){
          stroke(255); strokeWeight(4); point(px,py); strokeWeight(2); stroke(nCor);
        }
      }
    endShape();
     if (calcFreq.clicado){  
       curvaDiferencial();
     }
    strokeWeight(2); stroke(nCor);
  }
  
  public float fx(int x){
    return tela.x+Q*dt.v.v/ft.v.v*x;
  }

  public float fy(int y){
    return p0-y*fa/fm.v.v*Q;
  }
  
  
  //14/03/2016 - comecei a procurar a frequencia pelos picos minimos, criei vMin e pMin
  //                falta analisar os picos minimos e tirar os picos m\u00e1ximos
  public void curvaDiferencial(){
    float px;
    int vMax1=0,vMax2=0,pMax1=-1,pMax2=-1; // -1 no pMax para indicar que n\u00e3o foi encontrado
    int vMin1=0, vMin2=0, pMin1=-1, pMin2=-1;
    int pM1,pM2;
    int vMax=0, pMax=-1;
    int vMin=0, pMin=-1;
    //float vRuido=map(ruido.v.v,0,5,0,1023);
    qPicos=0;
    // procurar o valor dif m\u00e1ximo vMax => pMax (ponto k)
    for (int k=1; k<q.v.v; k++){
       dif[k-1]=v[k]-v[k-1];
       if (dif[k-1]>vMax) {
         vMax=dif[k-1];
         pMax=k-1;
        }
        if (dif[k-1]<vMin){
           vMin=dif[k-1];
           pMin=k-1;
        }
    }
    
 //==eliminei essa rotina que procura picos m\u00e1ximos para procurar os picos minimos - 14/03/2016===
 // procurar todos os pontos que est\u00e3o entre vMax e 2/3*vMax-vRuido
 /*   vMax=(int)(2.0/3.0*(float)vMax);
    qPicos=0;
    if (vMax>0){
      for (int k=0; k<q.v.v-1; k++){ 
         if (dif[k]>=vMax){
           qPicos++;
           picos[qPicos-1]=k;
         }
      }
      if (qPicos>=2){
         if (picos[1]-picos[0]>1){
         pMax1=picos[0]; vMax1=dif[pMax1];
         pMax2=picos[1]; vMax2=dif[pMax2];
         }
      }
    }

   // procurar todos os pontos que est\u00e3o entre vMax e 2/3*vMax-vRuido
    vMax=(int)(2.0/3.0*(float)vMax);
    qPicos=0;
    if (vMax<0){
      for (int k=0; k<q.v.v-1; k++){ 
         if (dif[k]>=vMax){
           qPicos++;
           picos[qPicos-1]=k;
         }
      }
      if (qPicos>=2){
         if (picos[1]-picos[0]>1){
         pMax1=picos[0]; vMax1=dif[pMax1];
         pMax2=picos[1]; vMax2=dif[pMax2];
         }
      }
    }

    if (pMax1>=0 && pMax2>=0){ //deve ser onda quadrada
       //println(n," Quadrada");
    }else{    // deve ser senoide (suave)
        //println(n," Senoide");
        //pMax1=-1; pMax2=-1;
        for (int k=0; k<q.v.v-2; k++){ // pegar 2 pontos de mudan\u00e7a do Zero
          //println("vMax1=",vMax1," vMax2=",vMax2);
          if (dif[k]>0 && dif[k+1]<=0){ // achou + para - (pico)
            //println("entrei ",dif[k],dif[k+1]);
            if (pMax1<0){
               vMax1=dif[k+1];
               pMax1=k+1;
               //println("pMax1=",pMax1," vMax1=",vMax1);
            } else if (pMax2<0){
              vMax2=dif[k+1];
              pMax2=k+1;
              // println("pMax2=",pMax2," vMax2=",vMax2);
              break;
            }
          }
        }
    }

*/    

   // procurar todos os pontos que est\u00e3o entre vMin e 2/3*vMin-vRuido
    vMin=(int)(2.0f/3.0f*(float)vMin);
    qPicos=0;
    if (vMin<0){
      for (int k=qInit; k<q.v.v-1; k++){ 
         if (dif[k]<=vMin){
           qPicos++;
           picos[qPicos-1]=k;
         }
      }
      if (qPicos>=2){
         if (picos[1]-picos[0]>1){
         pMin1=picos[0]; vMin1=dif[pMin1];
         pMin2=picos[1]; vMin2=dif[pMin2];
         }
      }
    }
    
    
    
    if (pMin1>=0 && pMin2>=0){ //deve ser onda quadrada
       //println(n," Quadrada");
    }else{    // deve ser senoide (suave)
        //println(n," Senoide");
        //pMax1=-1; pMax2=-1;
        for (int k=qInit; k<q.v.v-2; k++){ // pegar 2 pontos de mudan\u00e7a do Zero
          //println("vMax1=",vMax1," vMax2=",vMax2);
          if (dif[k]>0 && dif[k+1]<=0){ // achou + para - (pico)
            //println("entrei ",dif[k],dif[k+1]);
            if (pMin1<0){
               vMin1=dif[k+1];
               pMin1=k+1;
               //println("pMax1=",pMax1," vMax1=",vMax1);
            } else if (pMin2<0){
              vMin2=dif[k+1];
              pMin2=k+1;
              // println("pMax2=",pMax2," vMax2=",vMax2);
              break;
            }
          }
        }
    }
    
    
    
    // desenhar os fios diferenciais dif[]
    if (grafDif.clicado){
      strokeWeight(1); stroke(255);
      for (int k=qInit; k<q.v.v-1;k++){
          px=fx(k-qInit);
          if (px>tela.x+tela.w || px<tela.x) { break;}
          line(px,p0,px,fy(dif[k])); 
      }
      stroke(200,0,200);
      for (int k=qInit;k<qPicos;k++){
         px=fx(picos[k-qInit]);
         if (px>tela.x+tela.w || px<tela.x){ break;}
         line(px,p0,px,fy(dif[picos[k]]));
      }
    }
    
    
  //== 14/03/2016 - tirei para recalcular a frequencia com div negativo ====  
   //calcular a frequencia e per\u00edodo
 /*  tCalc.setV(0);
   fCalc.setV(0);
   if (pMax1>=0 && pMax2>=0){
      //desenhar os pMax1 e pMax2
      strokeWeight(5); stroke(255,0,255);
      point(fx(pMax1),p0);
      point(fx(pMax2),p0);
      strokeWeight(1);
     
     tCalc.setV(abs(pMax2-pMax1)*dt.v.v);
    fCalc.setV(1/tCalc.v);
    
    //mostrar a frequencia e o periodo
    textAlign(LEFT); fill(0);
    text(fCalc.printV()+"Hz ("+tCalc.printV()+"s)",medir.x,medir.y+29); 
   }
  */ 
 
   //calcular a frequencia e per\u00edodo - dif negativo 14/03/2016 
   tCalc.setV(0);
   fCalc.setV(0);
   if (pMin1>=0 && pMin2>=0){
      //desenhar os pMin1 e pMin2
      strokeWeight(5); stroke(255,0,255);
      point(fx(pMin1-qInit),p0);
      point(fx(pMin2-qInit),p0);
      strokeWeight(1);
     
     tCalc.setV(abs(pMin2-pMin1)*dt.v.v);
    fCalc.setV(1/tCalc.v);
    
    //mostrar a frequencia e o periodo
    textAlign(LEFT); fill(0);
    text(fCalc.printV()+"Hz ("+tCalc.printV()+"s)",medir.x,medir.y+29); 
   }
   
 
   
  }
  
  
  


  /*============================================================
     controle do retangulo de medi\u00e7\u00e3o na tela (faz medi\u00e7\u00f5es tempoxtens\u00e3o)
  =============================================================== */
    // mostrar o retangulo de sele\u00e7\u00e3o e os valores tempo x volts
    public void displayRect(){ 
      if (telaClicou){
         fill(nCor,50); stroke(nCor,255); strokeWeight(1);
         tracejado(xi,yi,xi+dx,yi+dy,3);
         fill(255);
         float vTemp=abs(dx)/(Q)*ft.v.v*1000.0f;
         //println("Q=",Q);
         String vh=nf(vTemp,0,1)+" ms";
         String fh=nf(1000/vTemp,0,1)+ " Hz";
         String vv=nf(abs(dy)/(Q)*fm.v.v,0,2)+" V";
         textAlign(RIGHT); text(vh+" "+fh,xi+dx-10,yi+dy/2);
         textAlign(LEFT); text(vv,xi+dx,yi+dy/2);
       }       
     }
     
     public void tracejado(float xi, float yi, float xf, float yf, float step){
        float temp;
        boolean faz=true;
        if (xi>xf) {
           temp=xf; xf=xi; xi=temp; 
        } 
        if (yi>yf) {
           temp=yf; yf=yi; yi=temp;
        }
        for (float x=xi; x<xf; x+=step){
           if (faz){
              line(x,yi,x+step,yi);
              line(x,yf,x+step,yf);
           } 
           faz=!faz;
        }
        for (float y=yi; y<yf; y+=step){
           if (faz){
              line(xi,y,xi,y+step);
              line(xf,y,xf,y+step);
           } 
           faz=!faz;
        }
     }

      // -- rotinas para fazer a medi\u00e7\u00e3o na "tela"
     public void telaMousePressionou(){
      // println("telaMousePressionou");
      // println("cor=",get(mouseX,mouseY));
       if (medir.clicado){ // acertar procurar qual cor de canal mais pr\u00f3ximo ao mouse
         if (mouseX>tela.x && mouseX<tela.x+tela.w && mouseY>tela.y && mouseY<tela.y+tela.h){
            telaClicou=true;
            //println("telaClicou=",telaClicou);
            xi=mouseX;
            yi=mouseY;    
            dx=0; dy=0;   
         }
       }
     }
     public void telaMouseArrastou(){
       //println("telaMouseArrastou");
       if (medir.clicado){
         if (telaClicou){
           
           if (mouseX>tela.x && mouseX<tela.x+tela.w && mouseY>tela.y && mouseY<tela.y+tela.h){
            dx=mouseX-xi;
            dy=mouseY-yi;
             //println("arrastando dx=",dx," dy=",dy);
           }
         }
       }
     }
     public void telaMouseSoltou(){
       //println("telaMouseSoltou telaClicou=",telaClicou);
       if (medir.clicado){
          if (telaClicou) {
            // println("dx=",dx," dy=",dy);
            if (abs(dx)<10 && abs(dy)<10){
               telaClicou=false;
          //     println("telaClicou= ",telaClicou);
            }
          } 
       }
     }

  
  //=== controle dos eventos do mouse ===  
  public boolean mouseClicado(){
     boolean ret=false;
     ret=chN.mouseClicado(); 
     //inv.mouseClicado();
     if (trigger.mouseClicado()){
        if (trigger.clicado){
           for (int k=0;k<4;k++){
              canal[k].trigger.clicado=false;
           }
           trigger.clicado=true;
           if (com.conectado){
              port.write("t"+str(n)); 
           }
        } else {
          if (com.conectado){
             port.write("tx"); 
          }
        }
     }
     fm.mouseClicado();
     ft.mouseClicado();
     if (medir.mouseClicado()){
        if (medir.clicado){
           for (int k=0;k<4;k++){
              canal[k].medir.clicado=false; 
           }
           medir.clicado=true;
        }
     };
     curva.mouseClicado();
     return ret;
  }
  
  public void mousePressionou(){
    fm.mousePressionou();
    ft.mousePressionou();
    p0Pressionou(); //se pegar o triangulo de p0
    p0TriggerPressionou(); // se pegar o triangulo de p0Trigger
    p0OutPressionou(); // se pegar o triangulo de p0Out
    telaMousePressionou();
  }
  
  public void mouseArrastou(){
    fm.mouseArrastou();
    ft.mouseArrastou();
    p0Arrastou(); // se arrastou o p0
    p0TriggerArrastou(); // se arrastou o p0Trigger
    p0OutArrastou(); // se arrastou o p0Out
    telaMouseArrastou();
  }
  
  public void mouseSoltou(){
    fm.mouseSoltou();
    ft.mouseSoltou();
    if (pegouP0) {
       pegouP0=false; 
    }
    if (pegouTrigger){
      vTrigger=constrain(PApplet.parseInt((p0-p0Trigger)/(fa/fm.v.v*Q)),0,1024);
      println("tv"+str(vTrigger)+".");
      if (com.conectado) {
        port.write("tv"+str(vTrigger)+".");
        
      }
      pegouTrigger=false;
    }
    if (pegouOut){
      pegouOut=false;
    }
    telaMouseSoltou();
  }
  
  public void mouseMoveu(){
     fm.mouseMoveu();
     ft.mouseMoveu(); 
  }
}
class CanalXYZ{
  Botao XYZ;
  int nCor;
  Botao selXYZ[]=new Botao[3];
  //CheckBox inv;
  CheckBox curvaSuave;
  CheckBox verCanais;
  int x,y,w,h;
  Dial fm;
  float p0;   // posi\u00e7\u00e3o tens\u00e3o zero (0)
  boolean pegouP0=false; // indica que mouse pegou o p0
  float mouseOffSet; // para deslocamento dos objetos (ex: p0)
  
  float fa=5.0f/(1023.0f); // (dividi por 4 pois tive falta de memoria no garagino) 16/09/2015 
                       // fator garagino (entrada analogica = 10bits) fa=5/1023unidades
  
  //Constructor
  CanalXYZ(int nCor_,int x_, int y_, int w_, int h_){
     x=x_; y=y_; w=w_; h=h_;
     nCor=nCor_;
     XYZ=new Botao("XYZ",x,y,w/2,15,nCor,nCor);
     //inv=new CheckBox("INV",x+w/2+8,y+4,12);
     for (int k=0; k<3;k++){
        selXYZ[k]=new Botao(str(k),x+w/2+5+k*(18+2),y+1,18,15);
        selXYZ[k].cor_ativo=cor[parseInt(selXYZ[k].tex)];
        selXYZ[k].clicado=true;
      }

     XYZ.clicado=false;
     fm=new Dial(escLog,altMove,!nInt,fmt,"","v/div",2f,100e-3f,20f,x+10,selXYZ[0].y+selXYZ[0].h+5,w-20,20);
     p0=tela.y+12*Q;//posi\u00e7\u00e3o da tens\u00e3o zero
     curvaSuave=new CheckBox("curva suave",fm.x,fm.y+fm.h+5,15);
     verCanais=new CheckBox("ver canais",curvaSuave.x,curvaSuave.y+curvaSuave.h+2,15);
    
  }
  
  public void display(){
     //== mostrar os controles ==
     strokeWeight(2); stroke(nCor); noFill();
     rect(x,y,w,h);
     XYZ.display();
     if (XYZ.clicado){
       //inv.display();
       for (int k=0;k<3;k++){
          selXYZ[k].display(); 
       }
       fm.display();
       curvaSuave.display();
       verCanais.display();
       //== mostrar a linha P0 de tens\u00e3o zero
       strokeWeight(1); stroke(nCor,150);
       line(tela.x,p0,tela.x+tela.w,p0);
       fill(nCor); noStroke();
       triangle(tela.x,p0,tela.x-20,p0-20,tela.x-20,p0+20);
       displayDados();
     }
  }
  
    //=== mostrar dados na Tela ===
  public void displayDados(){ 
    float px=0,py=0;
    int pz=color(255,255,255); 
    float temp=0;
    int cX=parseInt(selXYZ[0].tex);
    int cY=parseInt(selXYZ[1].tex);
    int cZ=parseInt(selXYZ[2].tex);
      // criar linha de controle do Y
      //strokeWeight(1); stroke(nCor,150);
      //line(tela.x-10,XYZy,tela.x+tela.w,XYZy);
      //fill(nCor); noStroke();
      //triangle(tela.x,XYZy,tela.x-20,XYZy-20,tela.x-20,XYZy+20);
      
      //cZ ser\u00e1 representado por cor red=0 -> blue=172
      // criar escala de cores para cZ 
      //rect(tela.x,tela.y,tela.w/3,30);
      //strokeWeight(1); stroke(nCor); noFill();
      strokeWeight(1); noStroke();
      if (verPontos.clicado){
        px=tela.x;
        py=tela.w/3.0f/172.0f;
        colorMode(HSB); //stroke(255); strokeWeight(2);
        for (int k=0; k<172; k++){
            fill(color(k,255,255));
            rect(px+k*py,tela.y,py,30);
        }
        colorMode(RGB); fill(255);textSize(20);
        textAlign(LEFT); text("0v",tela.x,tela.y+15); 
        textAlign(RIGHT); text("5v",tela.x+tela.w/3,tela.y+15);
        textAlign(CENTER); text("ch"+str(cZ),tela.x+tela.w/3/2,tela.y+15);
      }
      
      // criar eixos
      float px0=tela.x+10*Q-(p0-tela.y-2*Q); // posi\u00e7\u00e3o x central da tela
      strokeWeight(3);
      //if (inv.clicado){
      //  //stroke(cor[cY]); line(p0,p0,p0,p0-255*fa/fm.v.v*Q); // vertical
      //  //stroke(cor[cX]); line(p0,p0,p0-255*fa/fm.v.v*Q,p0);  // horizontal
      //  stroke(cor[cX]); line(px0,XYZy,px0,XYZy-255*fa/fm.v.v*Q); // vertical
      //  stroke(cor[cY]); line(px0,XYZy,px0+255*fa/fm.v.v*Q,XYZy); // horizontal
      //} else {
        //stroke(cor[cX]); line(p0,p0,p0,p0-255*fa/fm.v.v*Q); // vertical
        //stroke(cor[cY]); line(p0,p0,p0+255*fa/fm.v.v*Q,p0); // horizontal
        stroke(cor[cY]); line(px0,p0,px0,p0-1023*fa/fm.v.v*Q); // vertical
        stroke(cor[cX]); line(px0,p0,px0+1023*fa/fm.v.v*Q,p0); // horizontal
      //}
      strokeWeight(1); noFill();
      stroke(nCor); colorMode(HSB);
      beginShape();
        for (int k=0;k<q.v.v;k++){
           //if (!inv.clicado){
              //px=px0 + canal[cx].v[k]*fa/fm.v.v*Q;
              px=px0+canal[cX].v[k]*fa/fm.v.v*Q;
              py=p0 - canal[cY].v[k]*fa/fm.v.v*Q; 
           //} else {
           //   px=px0+canal[cY].v[k]*fa/fm.v.v*Q;
           //   py=XYZy-v[k]*fa/fm.v.v*Q;
           //}
           //colorMode(HSB);
           pz=color(map(canal[cZ].v[k],0,1024,0,172),255,255); //ch2
           //colorMode(RGB);
           //pz=lerpColor(color(255,0,0),color(0,0,255),map(canal[2].v[k],0,255,0,1)); // 0-255 cor
           //stroke(pz);
           
           //if (inv.clicado) {temp=px; px=py; py=temp;}
           if (curvaSuave.clicado) {
            curveVertex(px,py);
           } else {
            vertex(px,py);
           }
           if (verPontos.clicado){
              stroke(pz); strokeWeight(5); point(px,py); strokeWeight(1); //stroke(cor);
           }
        }
        strokeWeight(10); stroke(pz); point(px,py); strokeWeight(1); colorMode(RGB); stroke(nCor);
//        strokeWeight(10); stroke(255,0,255); point(px,py); strokeWeight(1); stroke(cor);
      endShape();
    
  }

  
  
    //=== mouse pega p0 e move ===
  public void p0MousePressionou(){
    if (XYZ.clicado){
      int pini=tela.x-20;
      int pfim=tela.x;
      if (mouseX>pini && mouseX<pfim && mouseY>(p0-20) && mouseY<(p0+20)){
        mouseOffSet=mouseY-p0;
        pegouP0=true;
      }
    }
  }
  
  public void p0MouseArrastou(){
   if (pegouP0){
      //p0=constrain(mouseY,tela.y,tela.y+tela.h)-mouseOffSet;
      p0=constrain(mouseY,tela.y+2*Q,tela.y+12*Q)-mouseOffSet;
   }
  }


  public void mouseClicado(){
    XYZ.mouseClicado();
    // canais selecionados do XYZ
    for (int k=0;k<3;k++){
       if(selXYZ[k].mouseClicado()){
          int j=10-4-(parseInt(selXYZ[0].tex)+parseInt(selXYZ[1].tex)+parseInt(selXYZ[2].tex));
          selXYZ[k].tex=str(j);
          selXYZ[k].cor_ativo=cor[j];
          selXYZ[k].clicado=true;
       } 
     }
     fm.mouseClicado();
     curvaSuave.mouseClicado();
     verCanais.mouseClicado();
  }
  
  public void mousePressionou(){
    fm.mousePressionou();
    p0MousePressionou(); //se pegar o triangulo de p0
  }
  
  public void mouseArrastou(){
    fm.mouseArrastou();
    p0MouseArrastou(); // se arrastou o p0
  }
  
  public void mouseSoltou(){
    fm.mouseSoltou();
    if (pegouP0) {
       pegouP0=false; 
    }
  }
  
  public void mouseMoveu(){
     fm.mouseMoveu();
  }

  
}
class CheckBox{
   int x,y,w,h;
   int tSize;  // textSize
   //color cor; // cor do fundo
   //color corBack; // cor do fundo do texto
   boolean clicado=false;
   boolean piscar=false;
   String tex, tex2="";
   //constructor
   CheckBox(String tex_, int x_, int y_, int tSize_){
     tex=tex_; x=x_; y=y_; tSize=tSize_; //h=h_; cor=cor_; corBack=corBack_;
     textSize(tSize);
     h=tSize;
     w=(int)textWidth(tex)+h+5; 
     //println("w=",w);
   }
   public void display(){
      //noFill(); stroke(255); strokeWeight(1);  rect(x,y,w,h);
      if (piscar){
        fill(map(millis()%1000,0,1000,0,125));
      }else{
        fill(0);
      }
      textAlign(LEFT,CENTER); 
      textSize(14); text(tex,x+h+5,y+h/2-2);
      if (clicado) {
         fill(0,200,0); 
      } else {
         noFill();
      }
      stroke(0);strokeWeight(1); rect(x+2,y+2,h-2,h-4); //ellipse(x+h/2,y+h/2,0.6*h,0.6*h); //rect(x+w-h,y,h,h);
      fill(0);
        //println("clicado=",clicado," tex2=",tex2);
        
      if (clicado && tex2.length()>0){
         text(tex2,x+5,y+1.75f*h);
        
      }
   }
   
  public boolean mouseClicado(){
     boolean r=false;
     if (mouseX>x && mouseX<x+w & mouseY>y && mouseY<y+h){
        clicado=!clicado;
        r=true;
     }
     return r;
  } 
}
class Com{
   Serial port;
   String ports[]=append(Serial.list(),"select serial");
   String portName;
   int indPort=ports.length-1;
   //String speed="115200";
   //String speed="250000";
   String speeds[]={"9600","115200","250000","select speed"};
   //int portSpeed;
   int indSpeed=speeds.length-1;
   int p=-1;
   int x,y,w,h,dh; 
   TextBox title, refresh, selectSerial, versionArduino, selectSpeed, onOff;
   boolean conectado=false;
   boolean erro=false;
   int cor=color(0);
   String tex;
   boolean estaSobre=false; // indica se o cursor do mouse est\u00e1 sobre a area do controle
   
   //constructor
   Com(Serial portt,int xt, int yt, int wt, int ht){
      x=xt; y=yt; w=wt; h=ht;
      dh=h/3;
      // line 1
      title= new TextBox("Configurar Serial",CENTER,x,y,PApplet.parseInt(0.7f*w),dh);
      refresh=new TextBox("refresh",CENTER,PApplet.parseInt(x+0.7f*w),y,PApplet.parseInt(0.3f*w),dh);
      //line 2
      selectSerial=new TextBox("select serial",CENTER,x,y+h/3,w,dh);
      //line 3
      selectSpeed=new TextBox("select speed",CENTER,x,y+2*h/3,PApplet.parseInt(0.4f*w),dh);
      versionArduino=new TextBox("",CENTER,PApplet.parseInt(x+0.4f*w),PApplet.parseInt(y+2*h/3),PApplet.parseInt(0.4f*w),dh);
      onOff=new TextBox("off",CENTER,PApplet.parseInt(x+0.8f*w),y+2*h/3,PApplet.parseInt(0.2f*w),dh);
      //onOff=new TextBox("off",CENTER,int(x+0.7*w),y,int(0.3*w),dh);
 } 
   public void display(){
     strokeWeight(1); stroke(0);fill(200);
     onOff.tex="off";
     if (conectado) {
         cor=color(0,255,0);
         onOff.tex="on";
        // fill(cor);
     } else if (erro) {
        cor=color(255,0,255);
     } else {
        cor=color(200);
      //  fill(200);
     }
     //fill(cor);
     title.display(cor);
     refresh.display(cor);
     selectSerial.display(cor);
     selectSpeed.display(cor);
     versionArduino.display(cor);
     onOff.display(cor);
     /*
     rect(x,y-20,11*u,20);
     rect(x,y,u,h); rect(x+u,y,4*u,h); rect(x+5*u,y,4*u,h); rect(x+9*u,y,2*u,h);  
     fill(0);textAlign(CENTER,CENTER); text("Configurar a Serial",x+w/2,y-12);
     //text("*",x+u/2,y+h/2); text(ports[indPort],x+3*u,y+h/2); text(speeds[indSpeed],x+7*u,y+h/2); 
     text("*",x+u/2,y+h/2); text(ports[indPort],x+3*u,y+h/2); text(speeds[indSpeed],x+7*u,y+h/2); 
     if (conectado) tex="on"; else tex="off";
     text(tex,x+10*u,y+h/2);//9*u,y+h/2);
     */
   }
   //int mouseLeftClick(){
     
   public void mouseMoveu(){
      if (mouseX>x && mouseX<x+w && mouseY>y & mouseY<y+h){
         if (estaSobre==false){
             cursor(HAND);
             estaSobre=true;
         }
      } else {
        if (estaSobre){
          cursor(ARROW);
          estaSobre=false;
        }
      }
   }
     
   public int mouseClicado(){
     int r=0;
     
     //if (mouseY>y && mouseY<y+h){
       if (refresh.mouseClicado()) { // recarregar a lista das COMs
         if (!conectado) {  // not connected
            ports=append(Serial.list(),"select serial");
            if (ports.length>1){
              indPort=ports.length-2;
            }else{
              indPort=ports.length-1;
            }
            selectSerial.tex=ports[indPort];
            indSpeed=1;
            selectSpeed.tex=speeds[indSpeed];
            versionArduino.tex="";
         }
       } else if (selectSerial.mouseClicado()) { // mudar porta serial
         //println("Com=mouseClicado");
         if (!conectado){
           indPort++;
           if (indPort>=ports.length) indPort=0;
         }
         selectSerial.tex=ports[indPort];
       } else if (selectSpeed.mouseClicado()) { // mudar speed (baudrate)
         if (!conectado){
           indSpeed++;
           if (indSpeed>=speeds.length) indSpeed=0;
         } 
         selectSpeed.tex=speeds[indSpeed];
       } else if (onOff.mouseClicado()){ // mudar X (desconectado) para ok (conectado)
          if (conectado){ // desconectar
              r=-1; // -1 => desconectar
              //port.stop();
              //conectado=false;
          } else {        // conectar
              //if (indPort<ports.length-1 && indSpeed<speeds.length-1){
              if (indPort<ports.length-1 && indSpeed<3){
                  //println("speeds[",indSpeed,"]=",speeds[indSpeed]);
                      //port=new Serial(this,"COM3",9600);
                    //port=new Serial(this,ports[indPort],int(speeds[indSpeed]));
                    r=1;  // retorna 1 para conectar
              }
          }
       }  
     //}
     return r;
   }

}
class Dial {
/*  dial(escala,alterar,nInt,formatar,tex,unid,v,vMin,vMax,x,y,w,h);
      escala=escLinear/escLog    //tipo da escala: linear ou logaritmica
      alterar=altMove/altSolta   //tipo de altera\u00e7\u00e3o: ao mover ou solta o mouse
      nTipo=nInt/nDec            //n deve ser inteiro ou decimal
      formatar=fmt / !fmt        // formatar o n\u00famero true/false
      
   constantes usadas na classe Dial 
     byte escLinear=0; // Dial com escala linear
     byte escLog=1;     // Dial com escala logar\u00edtimica (base 10)
     byte altMove=2; // mudar o valor ao arrastar o mouse "MouseDragged"
     byte altSolta=3; // mudar o valor ao soltar o bot\u00e3o do mouse "MouseReleased"
     byte nInt=4; // n \u00e9 inteiro (arredondar)
     byte nDec=5; // n \u00e9 decimal 
     boolean fmt=true; // fmt=true="formatar",  !fmt=false="n\u00e3o formatar" 
----------------- */
   
   int x,y,w,h;
   FmtNum v, vTemp;
   float vOld;
   //boolean nInt=false;
   float vMin, vMax;
   String unidade="";
   byte escala=escLinear;
   byte alterar=altSolta;  // alterar v quando MouseDrag ou MouseRelease
   int g; // usado para mudar simultaneamente os valores de varios controles qdo usar SHIFT
   //boolean linear=true;  // true=linear,  false=log10
   //boolean imediato=false; // true=alterar o valor do v quando mouseArrastou
                           // false=alterar o valor do v quando mouseSoltou
   String tex;
   boolean clicou=false;
   int cx, mouseOffSet;
   boolean mostrarTriangulos=false;
   boolean mostrarIncrementos=false;
   //boolean formatar=true;  // pede para a classe FmtNum n\u00e3o formatar no formato engenharia
   
  
   //constructor
   Dial(byte escala_, byte alterar_, boolean nInt_, boolean fmt_, String tex_,String unidade_, float v_, float vMin_, float vMax_, int x_, int y_, int w_, int h_){
       escala=escala_; alterar=alterar_; tex=tex_;
       unidade=unidade_;
       vMin=vMin_; vMax=vMax_;
       x=x_; y=y_; w=w_; h=h_;
       v=new FmtNum(v_,nInt_,fmt_);
       //formatar=fmt_;
       updateCx(); 
       vTemp=new FmtNum(v.v,nInt_,fmt_);
       g=0;
   } 
   Dial(byte escala_, byte alterar_, boolean nInt_, boolean fmt_, String tex_,String unidade_, float v_, float vMin_, float vMax_, int x_, int y_, int w_, int h_, int g_){
       escala=escala_; alterar=alterar_; tex=tex_;
       unidade=unidade_;
       vMin=vMin_; vMax=vMax_;
       x=x_; y=y_; w=w_; h=h_;
       v=new FmtNum(v_,nInt_,fmt_);
       //formatar=fmt_;
       updateCx(); 
       vTemp=new FmtNum(v.v,nInt_,fmt_);
      
       g=g_;
       grupo[g].qtd++;
       //println("Dial.tex=",tex," g=",g);
       //println("grupo[",g,"].qtd=",grupo[g].qtd);
   } 

   public void salvar(){
     vOld=v.v;  
   }
   
   public void restaurar(){
     setV(vOld);
   }
   
   public void setV(float v_){
       v.setV(v_); 
       updateCx();
   }
   
   public void updateCx(){
      //v=v_;
      
       cx=v2x(v.v); 
   }
   
   public void display(){
      display(color(0)); 
   }
   
   public void display(int cor){
      // faz retangulo
      //if (clicou) {stroke(100,0,0);} else {stroke(0);}
      stroke(cor);
      strokeWeight(1);   fill(200);  rect(x,y,w,h); 
      // faz o valor v
      noStroke();  fill(0,255,255); rect(x+1,y+1,cx-x-2,h-2);
      
      if (mostrarIncrementos){
          fill(0); stroke(0); textSize(10);
         text("-100",x,y+5); 
         text("-10",x+w/6,y+5);
         text("-1",x+2*w/6,y+5);
         text("+1",x+3*w/6,y+5);
         text("+10",x+4*w/6,y+5);
         text("+100",x+5*w/6,y+5);
      }
      if (mostrarTriangulos){
        // faz o triangulo do cursor
         fill(250,250,0); stroke(0);
        triangle(cx,y+3*h/4,cx-5,y+h,cx+5,y+h); //rect(cx-10,y,20,h);
        triangle(cx,y+h/4,cx-5,y,cx+5,y); //rect(cx-10,y,20,h);
      }

      // imprimir as linhas para delimitar as 6 areas de + -
      stroke(0);
      for (int k=0;k<5;k++){ 
        float vx=x+(k+1)*0.17f*w;
        if (k==2){line(vx,y,vx,y+0.2f*h);} 
          else {line(vx,y,vx,y+0.1f*h);} 
       
      }

      
      //faz o texto
      fill(0); strokeWeight(2); textSize(12);  textAlign(CENTER,CENTER); 

      String t=tex+" ";
      if (clicou) {
        if (alterar==altSolta){
          t+=vTemp.printV();
        } else {
          t+=v.printV();
        }
      } else {
        t+=v.printV();
      }
      text(t+unidade,x+w/2,y+h/2-2);
  
  
   }
   
   /*
   String fmt(float v_){
     String t;
     int i=round((log(v_)/log(10))/3);
     char u[]={'p','n','u','m',' ','k','M','G','T'};
     t=nf(v_*pow(10,-3*i),0,1)+u[i+4];
     if (t.charAt(0)=='0'){ // se o valor do primeiro caracter e' zero, ent\u00e3o subir escala (x1000)
        t=nf(1000*v_*pow(10,-3*i),0,1)+u[i+4-1]; 
     }
     return t; 
   }
   */
   
 /*  int fmtV(float v_){ //enviar apenas a parte inteira (1-999)
     int i=round((log(v_)/log(10))/3);
     int vi=int(v_*pow(10,-3*i));
     if (vi<=0) {
        vi=int(1000*v_*pow(10,-3*i)); 
     }
     println("vi=",vi);
     return vi;
     
   }
*/   
   
   public int v2x(float v_){
      if (escala==escLinear) {
        return (int)map(v_,vMin,vMax,x,x+w);
      } else {
        return (int)map(log(v_)/log(10),log(vMin)/log(10), log(vMax)/log(10),x,x+w); 
      }
   }
   
   public float x2v(int cx_){
     if (escala==escLinear){
       return map(cx_,x,x+w,vMin,vMax);
     } else{
       return pow(10,map(cx_,x,x+w,log(vMin)/log(10),log(vMax)/log(10)));
     }  
   }
   
   
   public void grupoUpdate(){
     if (g>0){
        if (grupo[g].conta>0){
           grupo[g].conta--;
           setV(grupo[g].v);
           if (grupo[g].conta<=0) grupo[g].v=0;
        }
     }
       
   }
   
   
   public boolean mouseClicado(){ // Soma/Subtrai 1,10 ou 100 do valor => true se alterou o valor
     boolean alterou=false;
     float v2=0;
     if (mouseX>x && mouseX<x+w && mouseY>y && mouseY<y+h){
       alterou=true;
       int p=(int)map(mouseX,x,x+w,1,7);
       //int p=round(map(mouseX,x,x+w,1,5));
       //println("p=",p);
       //println("fmtV=",fmtV(v));
       switch (p) {
          case 1: // subtrair 100
            v2=v.addN(-100);
            break;
          case 2: // subtrair 10 em 10
             //println("-10");
             v2=v.addN(-10);
           break;
          case 3: // subtrair de 1 em 1
            //println("-1");
            v2=v.addN(-1);
           break;
          case 4: // somar de 1 em 1
            //println("+1");
            v2=v.addN(+1);
           break;
          case 5: // somar de 10 em 10
            //println("+10");
            v2=v.addN(+10);
           break; 
          case 6: //somar
            v2=v.addN(+100);
            break;
       }
       if (v2<vMin) {
          v.setV(vMin); 
       } else if (v2>vMax){
          v.setV(vMax); 
       } else {
          v.setV(v2); 
       }
       updateCx();
       ifShiftAlterarGrupo();       
     } 
     return alterou;
   }

   public void mouseMoveu(){
      if (mouseY>y && mouseY<y+h) {
        if (mouseX>cx-10 && mouseX<cx+10){
         // println("mouseMoveu Dial");
          mostrarTriangulos=true;
        } else {
          mostrarTriangulos=false;
        }
        if (mouseX>x && mouseX<x+w && keyPressed && keyCode==CONTROL){
          println("mostrarIncrementos=" + mostrarIncrementos);
           mostrarIncrementos=true; 
        } else {
           mostrarIncrementos=false;
        }
      }  else {
        mostrarTriangulos=false;
      }   
   }
   
   public void mousePressionou(){
     if (mouseButton==LEFT){
      if (mouseY>y && mouseY<y+h) {
        if (mouseX>cx-10 && mouseX<cx+10){
          //println("mousePressionado"); 
          clicou=true; 
           vTemp.setV(v.v);
           mouseOffSet=mouseX-cx;
           //println("cx=",cx);
        }
      }
     }
   }
   
   public boolean mouseArrastou(){ // retorna true se \u00e9 para enviar o comando para Garagino
      //println("Dial.mouseArrastou");
      boolean enviar=false;
      if (clicou){
         cx=constrain(mouseX-mouseOffSet,x,x+w);
         if (alterar==altMove){ // \u00e9 para alterar Imediatamente enquanto Mover o Mouse
            vTemp.setV(x2v(cx)); // converte o x para v
              v.setV(vTemp.v); 
              enviar=true;   // enviar o comando de alterar para o Garagino!
              ifShiftAlterarGrupo(); // se tiver SHIFT ent\u00e3o alterar Grupo
         }else{
            vTemp.setV(x2v(cx));
         }
      }
     return enviar; 
   }
   
   public boolean mouseSoltou(){ // retorna true se \u00e9 para enviar o comando para o Garagino
     boolean enviar=false;
      if (clicou) {
        clicou=false;
        if (alterar==altSolta){
           if (mouseY>y-10 && mouseY<y+h+10) { // && mouseX>x-15 && mouseX<x+w+15){
               v.setV(vTemp.v); // \u00e9 para alterar quando Soltar o Mouse
               enviar=true;  // enviar comando de alterar para o Garagino!
               ifShiftAlterarGrupo(); // se tiver SHIFT ent\u00e3o alterar Grupo
            } else{
               cx=v2x(v.v);
           }
        }
    } 
    return enviar;
   }
   
   public void ifShiftAlterarGrupo(){
     if (keyPressed && key==CODED && keyCode==SHIFT){
        grupo[g].v=v.v;
        grupo[g].conta=grupo[g].qtd; //quantidade de controles que ir\u00e3o sincronizar o valor
     }
   }
   
}
class FmtNum{
   float v; // valor em float
   float n;  // parte numerica do valor formatado
   boolean nInt=false; // true=n arredondar n para inteiro
   boolean formatar=true; // formatar o texto no formato Engenharia (nu=numero-escala)
   char u; // parte unidade do valor formatado
   int i; // indice da unidade
   char unid[]={'f','p','n','u','m',' ','k','M','G','T','P'}; //pico(-12),nano(-9),micro(-6),mili(-3), (-0),kilo(3),mega(6),giga(9),tera(12)
     // femto, pico, nano, micro, mili, ., kilo, mega, giga, tera, peta

   //constructor
   FmtNum(float v_,boolean nInt_,boolean fmt_){
     v=v_;
     nInt=nInt_;
     v2nu(v);
     formatar=fmt_;
   }
   FmtNum(float v_,boolean nInt_){
     v=v_;
     nInt=nInt_;
     v2nu(v);
     formatar=true;
   } 
   
   public String printV(){
     if (nInt){            // inteiro
       if (formatar){      //   inteiro formatado (nu)
         return nf(n,0,0)+u;
       } else {            //  inteiro n\u00e3o formatado (nu)
         return str(PApplet.parseInt(v));
       }
     } else{                // decimal (n\u00e3o inteiro)
       if (formatar){        //  decimal formatado (nu)
         return str(n)+u;
       } else {              // decimal n\u00e3o formatado (nu)
          return str(v); 
       }
     }  
   }
   
   public void setV(float v_){
     v=v_;
     v2nu(v);  
   }
   
   public float getV(){
     if (nInt){            // inteiro
         return PApplet.parseInt(n)*pow(10,(i-5)*3);
     } else{                // decimal (n\u00e3o inteiro)
         return v; 
     }  
   }

   public void setNInt(){
       n=round(n);
       nu2v();
   }
   
   // somar/subtrair valores em n
   public float addN(float k){ // adicionar/subtrair n (se u=' ' descer casa decimal)
                     // pequeno 1, grande 10 (se u=' ' pequeno=0.1, grande 1)
      float n2=PApplet.parseInt(n);
      int i2=i;      
      if (n2+k>0){
        n2+=k;
      } else {
        if (i2>0){
           i2--;
           n2=1000+k; 
        }
      }      
      return n2*pow(10,(i2-5)*3);
   } 
   
   public void v2nu(float v_){
    i=constrain(PApplet.parseInt((log(v_)/log(10)+15)/3),0,unid.length-1); // calcular o indice do expoente do numero (v_) na base 10
    if (nInt){
      n=round(v_/pow(10,(i-5)*3));
    } else {
      n=round((v_/pow(10,(i-5)*3))*10.0f)/10.0f;
    }
    u=unid[i];
   }
   
   public void nu2v(){
      v=n*pow(10,(i-5)*3);
      v2nu(v); 
   }
}
class Grupo{
  float v=0;
  int qtd=0;
  int conta=0;
  
  //constructor
  //Grupo(){
  //   v=0;
  //   qtd=0;
  //   conta=0;
  //}
}
class Painel{
   int x,y,w,h;
   String tex="";
   String tex2="";
   Boolean piscar=false;
   
   //constructor
   Painel(String tex_, int x_, int y_, int w_, int h_){
     tex=tex_;
     x=x_; y=y_; w=w_; h=h_;
   }
   
   public void display(){
      strokeWeight(1); fill(200); stroke(0);
      rect(x,y,w,h);
      if (piscar){
        fill(map(millis()%1000,0,1000,0,255));
      }else {
        fill(0);
      } 
      
      textAlign(LEFT); text(tex+" "+tex2,x+5,y+textAscent());
      
   } 
}
class Tela{
   int x,y,w,h;
   
   //constructor
   Tela(float xi, float yi, float wi, float hi){
      x=PApplet.parseInt(xi); y=PApplet.parseInt(yi); w=PApplet.parseInt(wi); h=PApplet.parseInt(hi);
   } 
   public void display(){
      fill(0); stroke(0,0,255); strokeWeight(1);// noStroke();
      rect(x,y,w,h); 
      stroke(100);
      for (float lin=y; lin<y+h; lin+=h/12){
         line(x,lin,x+w,lin);
      }
      for (float col=x; col<x+w; col+=w/10){
          line(col,y,col,y+h);
       } 
   }
}
class TextBox{
   int x,y,w,h;
   int hAlign;
   String tex;
   //constructor
   TextBox(String texi,int hAligni, int xi, int yi, int wi, int hi){
     tex=texi; hAlign=hAligni;
     x=xi; y=yi; w=wi; h=hi;
   }
   public void display(int bgColor){
      fill(bgColor);
      rect(x,y,w,h);
      fill(0);
      if (hAlign==LEFT){
        textAlign(LEFT,CENTER);
        text(tex,x+5,y+h/2);
      } else{
        textAlign(CENTER,CENTER);
        text(tex,x+w/2,y+h/2);
      }
   }
   
   public boolean mouseClicado(){
       boolean r=false;
       if (mouseX>x && mouseX<x+w && mouseY>y && mouseY<y+h){
         r=true;
        // println(tex," mouseClicado=",r);
       }
       return r;
   }
}
  public void settings() {  size(660, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "oscilloscope_4ch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
