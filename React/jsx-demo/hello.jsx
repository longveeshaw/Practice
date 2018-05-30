class _Main{
	static function main(args:string[]):void{
		log "hello, world!";

        var s1:Nubllable.<string>;
        s1="abc";
        s1=null;
        
        var m={
            hello:"world"
        };
        log m["hello"];
	}

    function guessTheType(v:variant){
        if(typeof v=="string"){
            log "v is string and the value is:"+v as string;

        }
        else{
            log "v is not string";
        }
    }
}


var doubled=[1,2,3].map(function(n){
	return 2*n;
})
