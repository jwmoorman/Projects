#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

typedef struct place{
	int id;
	int numTokens;
}place;

typedef struct transition{
	int *inputPlaces;
	int *outputPlaces;
	int numInput;
	int numOutput;
}transition;


int checkState(place **statesVisited, int numVisited, place nextPlace[], int numPlaces){
	int i, j;	
	//printf("num visisted:%d\n", numVisited);
	//printf("num places:%d\n", numPlaces);
	for(i=0; i< numVisited; i++){
		int numEqual = 0;
		for(j=0; j < numPlaces; j++){
		//	printf("visited numTokes: %d\n",statesVisited[i][j].numTokens);	
			if(statesVisited[i][j].numTokens == nextPlace[j].numTokens){
				numEqual++;
		        }	
			if (numEqual == numPlaces){
				return 0;
			}
		}
	}
	return 1;
}


void printPlace(place p) {
	printf("   id: %d\n   numTokens: %d\n",p.id,p.numTokens);
}

int isActive(transition t, place pl[]) {
	int active = 1;
	int numInputs = sizeof(t.inputPlaces) / sizeof(t.inputPlaces[0]);
	int i = 0;
	for (i = 0; i < numInputs; i++){
		if(pl[t.inputPlaces[i]-1].numTokens < 1){			
			active = 0;
		}
	}
	return active;
}

place createPlace(int id, int numTokens) {
	place pl;
	pl.id = id;
	pl.numTokens = numTokens;
	return pl;
}

int fire(place pl[], transition t){
	int numInput = t.numInput;
	int numOutput = t.numOutput;
	int i;
	if(!isActive(t, pl)){
		return 0;
	}
	for(i=0; i < numInput; i++){
		pl[t.inputPlaces[i]-1].numTokens--;
	}
	for(i=0; i < numOutput; i++){
		pl[t.outputPlaces[i]-1].numTokens++;
	}
	return 1;
}



void printState(place *pl,int numPlaces){
	int i;
	for(i=0; i < numPlaces; i++){
		printPlace(pl[i]);
	}
}


place *copyState(place pl[], int numPlaces){
	place *newState = (place *)malloc(sizeof(place)*numPlaces);
	int i;
	for(i=0; i < numPlaces; i++){
		newState[i] =pl[i];
	}
	return newState;
}

int transitionFound(FILE *file){
	int x; 
	int check = 0;
	while( check == 0){
		x = fgetc(file);
		if(x == 'T'){
			check = 1;
			x = fgetc(file);
			x = x - 48;
		}
	}
	printf("    transition number: %d\n",x);
	return x;
}

int inputOutputFound(FILE *file, place pl[] ){
	int numberOfPlaces = 0, x = 0, y = 0;
	int check = 0;
	while(check == 0){
		x = fgetc(file);
		if(x == '\n'){
			check = 1;
			break;
		}
		if(x == '('){
			fgetc(file);
			x = fgetc(file);
			x = x - 48;
			printf("    input id: %d\n", x);
			fgetc(file);
			fgetc(file);
			y = fgetc(file);
			y = y - 48;
			printf("    number of Tokens: %d\n", y);
			pl[numberOfPlaces]= createPlace(x,y);
			numberOfPlaces++;
		}
	
	}
	copyState(pl, numberOfPlaces);
	return numberOfPlaces;
}

int main(int argc, char *argv[]) {

// Start input section///////
printf("\n---start input tests---\n");
FILE *file = fopen(argv[1], "r");
if(file == 0){
	printf("error opening file\n");
	return 0;
}
int x;
int y;
while( ( x = fgetc(file) ) != EOF ){
	if(x == 'T'){
		printf("SUCCESS: found transition\n");
			int tranNum = transitionFound(file);
	}
	else if(x == 'I'){
		printf("SUCCESS: found input place\n");
		place p[100];
		inputOutputFound(file,p);
		//test value
		printf("  place info test: %d\n",p[0].numTokens);
	}
	else if(x == 'O'){
		place p[100];
		printf("SUCCESS: found output place\n");
		inputOutputFound(file,p);
		//test
		printf("  output place id value: %d\n",p[0]);
	}

}

fclose(file);
printf("---end input tests---\n\n");
/// End input section ////



int numVisited = 0;
transition *trans = (transition *)malloc(sizeof(transition)*3);
trans[0].inputPlaces = (int *)malloc(sizeof(int)*2);
trans[0].numInput = 1;
trans[1].inputPlaces = (int *)malloc(sizeof(int)*2);
trans[1].numInput = 1;
trans[0].outputPlaces = (int *)malloc(sizeof(int)*2);
trans[0].numOutput = 1;
trans[1].outputPlaces = (int *)malloc(sizeof(int)*2);
trans[1].numOutput = 1;
int numPlaces = 2;
place pl[numPlaces];
//parameter 1 is id parameter 2 is number of tokens
pl[0] = createPlace(1,1);
pl[1] = createPlace(2,0);

place **visited;
visited = (place **)malloc(sizeof(place *)*50);
visited[0] = copyState(pl, numPlaces);
numVisited++;

trans[0].inputPlaces[0] = 1;
trans[1].inputPlaces[0] = 2;

trans[0].outputPlaces[0] = 2;
trans[1].outputPlaces[0] = 1;

printf("is Active: %d\n", isActive(trans[0],pl));

//printPlace(pl[0]);
printState(pl, numPlaces);
fire(pl, trans[0]);
printf("check state: %d\n", checkState(visited, numVisited, pl, numPlaces));
visited[++numVisited] = copyState(pl, numPlaces);
printState(pl, numPlaces);
fire(pl, trans[1]);
printf("check state: %d\n", checkState(visited, numVisited, pl, numPlaces));
visited[++numVisited] = copyState(pl, numPlaces);
printState(pl, numPlaces);

//trans[0].inputPlaces[0] = &pl;


//trans[0].inputPlaces[1].id = 2;
//trans[0].inputPlaces[1].numTokens = 2;

//printf("%d\n",isActive(trans[0]));

}

