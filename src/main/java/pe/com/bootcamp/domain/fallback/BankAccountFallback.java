package pe.com.bootcamp.domain.fallback;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BankAccountFallback {
	
	private Object object;

	public Mono<UnitResult<BankAccount>> fallback_create(BankAccount entity, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };				
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));
		return Mono.just(new UnitResult<BankAccount>(entity,true,causes.toString()));
	}
	
	public Mono<UnitResult<BankAccount>> fallback_update(BankAccount entity, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };				
		return Mono.just(new UnitResult<BankAccount>(entity,true,causes.toString()));			
	}
	
	public Mono<UnitResult<BankAccount>> fallback_saveAll(Flux<BankAccount> entities, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };
		return Mono.just(new UnitResult<BankAccount>(entities.blockLast(),true,causes.toString()));
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));			
	}
	
	public Mono<UnitResult<BankAccount>> fallback_findById(String id, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };
		BankAccount account=new BankAccount();
		account.setId(id);
		return Mono.just(new UnitResult<BankAccount>(account,true,causes.toString()));
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));			
	}
	
	public Mono<UnitResult<BankAccount>> fallback_findByClientIdentNum(String dni, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };
		BankAccount account=new BankAccount();
		account.setClientIdentNum(dni);
		return Mono.just(new UnitResult<BankAccount>(account,true,causes.toString()));
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));			
	}
	
	public Mono<UnitResult<BankAccount>> fallback_findByAccountNumber(String accountNumber, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };
		BankAccount account=new BankAccount();
		account.setAccountNumber(accountNumber);;
		return Mono.just(new UnitResult<BankAccount>(account,true,causes.toString()));
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));			
	}
	
	public Mono<UnitResult<BankAccount>> fallback_findAll(Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };				
		return Mono.just(new UnitResult<BankAccount>());			
	}
	
	public Flux<BankAccount> fallback_findAllStreaming(Throwable throwable) throws Exception{
		throw new Exception("Timeout expired");			
	}
	
	public Mono<ResultBase> fallback_deleteById(String id, Throwable throwable){
		int code = 1002;
		String[] causes = new String[] { "Timeout expired" };	
		BankAccount account=new BankAccount();
		account.setId(id);
		return Mono.just(new UnitResult<BankAccount>(account,true,causes.toString()));
		//return Mono.just(new UnitResult<BankAccount>(true,"Request fails. It takes long time to response", code, causes));			
	}
}
